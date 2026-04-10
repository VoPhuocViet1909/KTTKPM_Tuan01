const amqp = require("amqplib");

const AMQP_URL = process.env.AMQP_URL || "amqp://localhost:5672";
const AMQP_EXCHANGE = process.env.AMQP_EXCHANGE || "movie.events";
const AMQP_ROUTING_KEY = process.env.AMQP_ROUTING_KEY || "USER_REGISTERED";

let channelPromise;

async function getChannel() {
  if (!channelPromise) {
    channelPromise = amqp
      .connect(AMQP_URL)
      .then(async (connection) => {
        connection.on("error", () => {
          channelPromise = null;
        });
        connection.on("close", () => {
          channelPromise = null;
        });

        const channel = await connection.createChannel();
        await channel.assertExchange(AMQP_EXCHANGE, "topic", { durable: true });
        return channel;
      })
      .catch((error) => {
        channelPromise = null;
        throw error;
      });
  }

  return channelPromise;
}

async function publishUserRegistered(payload) {
  try {
    const channel = await getChannel();
    const content = Buffer.from(JSON.stringify(payload));
    channel.publish(AMQP_EXCHANGE, AMQP_ROUTING_KEY, content, {
      contentType: "application/json",
      persistent: true,
      timestamp: Date.now(),
      type: "USER_REGISTERED",
    });
  } catch (error) {
    console.warn("RabbitMQ publish skipped:", error.message);
  }
}

module.exports = {
  publishUserRegistered,
};
