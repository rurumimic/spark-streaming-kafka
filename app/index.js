const Koa = require('koa');
const app = new Koa();
const serve = require('koa-static');
const path = require('path');
const kafka = require('kafka-node');
const bodyParser = require('koa-bodyparser');
const Router = require('koa-router');
const router = new Router();

const Producer = kafka.Producer;
const Consumer = kafka.Consumer;
const client = new kafka.KafkaClient({kafkaHost: '127.0.0.1:9092'});
const producer = new Producer(client);
const consumer = new Consumer(
  client,
  [
    { topic: 'topic-to-node', partition: 0 }
  ],
  {
    autoCommit: true
  }
);

app.use(serve(path.join(__dirname, '/client')));

// Consumer
consumer.on('message', function (message) {
  console.log('Return:', message.value);
});

consumer.on('offsetOutOfRange', function (topic) {
  topic.maxNum = 2;
  offset.fetch([topic], function (err, offsets) {
    if (err) {
      return console.error(err);
    }
    var min = Math.min.apply(null, offsets[topic.topic][topic.partition]);
    consumer.setOffset(topic.topic, topic.partition, min);
  });
});

consumer.on('error', function (err) {
  console.log('Consumer Error', err);
});

// Producer
producer.on('error', function (err) {
  console.log('Producer Error', err);
});

// API
router.post(
  '/subscribe',
  async ctx => {
    const message = ctx.request.body.message;

    ctx.status = 201;

    producer.send([
      { topic: 'topic-from-node', partition: 0, messages: message, attributes: 0 }
    ], function (err, result) {
      // console.log('Produce Result: ', err || result);
    });
  }
);

// Bodyparser
app.use(bodyParser());

// Router
app.use(router.routes());
app.use(router.allowedMethods());

// Run server
const port = process.env.PORT || 3000;
const mode = process.env.NODE_ENV || 'development';
app.listen(port, () => {
  console.log(`Server run in ` + mode + ` mode.`);
});