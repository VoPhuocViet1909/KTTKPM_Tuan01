const express = require("express");
const amqp = require("amqplib");

// Configuration
const CONFIG = {
  BROKER_URL: "amqp://user:password@rabbitmq:5672",
  QUEUES: {
    MAIN: "order_queue",
    DLQ: "order_queue.dlq",
  },
};

class ProducerService {
  constructor() {
    this.connection = null;
    this.channel = null;
  }

  async initialize() {
    try {
      console.log("[Producer] Attempting to connect to message broker...");
      this.connection = await amqp.connect(CONFIG.BROKER_URL);
      this.channel = await this.connection.createChannel();

      // Configure queues with DLQ strategy
      await this.channel.assertQueue(CONFIG.QUEUES.MAIN, {
        durable: true,
        deadLetterExchange: "",
        deadLetterRoutingKey: CONFIG.QUEUES.DLQ,
      });

      console.log("[Producer] Successfully connected & Queue ready.");
    } catch (error) {
      console.error(`[Producer] Connection error: ${error.message}`);
      console.log("[Producer] Retrying in 5 seconds...");
      setTimeout(() => this.initialize(), 5000);
    }
  }

  publish(data) {
    if (!this.channel) {
      console.error("[Producer] Channel not available");
      return false;
    }

    const payload = Buffer.from(JSON.stringify(data));
    return this.channel.sendToQueue(CONFIG.QUEUES.MAIN, payload, {
      persistent: true,
    });
  }
}

// Setup Express App
const app = express();
const producer = new ProducerService();

app.use(express.json());

// Initialize RabbitMQ connection
producer.initialize();

app.post("/send", async (req, res) => {
  try {
    const { message, orderId } = req.body;

    // Validation
    if (!message || !orderId) {
      return res.status(400).json({
        success: false,
        error: "Bad Request: Missing 'message' or 'orderId'.",
      });
    }

    const orderData = {
      message,
      orderId,
      createdAt: new Date().toISOString(),
    };

    const isSent = producer.publish(orderData);

    if (isSent) {
      console.log(`[Producer] Message sent for Order ID: ${orderId}`);
      return res.status(200).json({
        status: "sent",
        dataSent: orderData,
      });
    } else {
      throw new Error("Failed to send message to queue");
    }
  } catch (error) {
    res.status(500).json({ success: false, error: error.message });
  }
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`[Producer] Server is running on port ${PORT}`);
});
