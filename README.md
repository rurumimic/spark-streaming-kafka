# Spark Streaming + Kafka

## Examples Versions

- Sparks
  - Apache
    - Latest
      - wordcounter
        - OpenJDK 17.0.5
        - Scala 2.13.10
  - Cloudera
    - CDH 7.1.7
      - wordcounter
        - OpenJDK 11.0.9.1
        - Scala 2.12.17

---

## Setup

### Docker Compose

- spark-streaming-kafka
  - wordcounter
- spark-streaming-kafka-zookeeper
  - wordcounter
    - [docker-compose.sample.yml](docker/spark-streaming-kafka-zookeeper/wordcounter/docker-compose.sample.yml)

`docker-compose.sample.yml`: Line 24. Set `KAFKA_ADVERTISED_HOST_NAME: # <HOST_IP>`

```bash
cp docker-compose.sample.yml docker-compose.yml
```

Do not use localhost or 127.0.0.1 as the host ip.

```bash
# mac
ifconfig | grep inet

inet 192.168.XXX.XXX netmask 0xffffff00 broadcast 192.168.XXX.255
```

```yml
KAFKA_ADVERTISED_HOST_NAME: 192.168.XXX.XXX
```

### Setup Node Backend

```bash
cd app
yarn
```

---

## Run

Start a cluster:

```bash
docker compose up -d
```

### Spark UI

[localhost:4040/streaming](http://localhost:4040/streaming)

### Kafka UI

[localhost:8080](http://localhost:8080)

### Kafka Consumer Node Client

Start a node server:

```bash
node index.js
```

Go to: [localhost:3000](http://localhost:3000)

Send:

> Coke or anthracite is heated to incandescence by an air blast
> in a generator lined with fire-brick, and the heated products
> of combustion as they leave the generator and enter
> the superheaters are supplied with more air, which causes
> the combustion of carbon monoxide present in the producer
> gas and heats up the fire-brick baffles with which
> the superheater is filled.

In Terminal:

```bash
Return: [
   (the,7), (with,3), (and,3), (is,2), (generator,2), 
   (heated,2), (in,2), (which,2), (of,2), (combustion,2), 
   (are,1), (up,1), (carbon,1), (blast,1), (gas,1), 
   (superheaters,1), (air,,1), (filled.,1), (a,1), (heats,1), 
   (anthracite,1), (leave,1), (or,1), (superheater,1), (to,1), 
   (as,1), (enter,1), (products,1), (more,1), (baffles,1), 
   (air,1), (by,1), (they,1), (lined,1), (incandescence,1), 
   (monoxide,1), (causes,1), (supplied,1), (producer,1), (an,1), 
   (fire-brick,,1), (fire-brick,1), (present,1), (Coke,1)
]
```

---

## Clean up

```bash
docker compose down -v
```

---

## Build sbt

- sbt shell: assmbely
  - output: `spark/target/scala-2.12/wordcounter.jar`
