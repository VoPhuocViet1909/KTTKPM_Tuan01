const amqp = require("amqplib");

const SYSTEM_CONFIG = {
  URI: "amqp://user:password@rabbitmq:5672",
  Q_MAIN: "order_queue",
  Q_DEAD: "order_queue.dlq",
};

class ConsumerWorker {
  constructor() {
    this.channel = null;
  }

  async start() {
    try {
      console.log("[Worker] Starting service...");
      const conn = await amqp.connect(SYSTEM_CONFIG.URI);
      this.channel = await conn.createChannel();

      // Ensure DLQ exists first
      await this.channel.assertQueue(SYSTEM_CONFIG.Q_DEAD, { durable: true });

      // Ensure Main Queue exists with DLQ binding
      await this.channel.assertQueue(SYSTEM_CONFIG.Q_MAIN, {
        durable: true,
        deadLetterExchange: "",
        deadLetterRoutingKey: SYSTEM_CONFIG.Q_DEAD,
      });

      console.log("[Worker] Waiting for tasks...");

      // Start consuming
      this.channel.consume(SYSTEM_CONFIG.Q_MAIN, (msg) =>
        this.handleMessage(msg),
      );
    } catch (err) {
      console.error("[Worker] Startup failed:", err.message);
      console.log("[Worker] Will retry connection in 3s...");
      setTimeout(() => this.start(), 3000);
    }
  }

  async handleMessage(msg) {
    if (!msg) return;

    const rawContent = msg.content.toString();
    console.log(`[Worker] Received payload: ${rawContent}`);

    try {
      const data = JSON.parse(rawContent);

      // Business Logic Validation
      if (!data.orderId) {
        throw new Error("Invalid Data: Missing 'orderId'");
      }

      // Simulate heavy processing task
      await this.processTask(3000);

      console.log("[Worker] Task completed successfully.");
      this.channel.ack(msg); // Confirm success
    } catch (error) {
      console.error(`[Worker] Processing failed: ${error.message}`);
      console.log("[Worker] Rejecting message -> Sending to DLQ.");

      // Negative Ack with requeue=false (sends to DLQ)
      this.channel.nack(msg, false, false);
    }
  }

  processTask(duration) {
    return new Promise((resolve) => setTimeout(resolve, duration));
  }
}

// Run the worker
const worker = new ConsumerWorker();
worker.start();
