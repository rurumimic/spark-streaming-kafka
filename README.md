# Spark streaming + Kafka

## Setup

`docker-compose.yml`: Line 24. Set `KAFKA_ADVERTISED_HOST_NAME: # <HOST_IP>`

Do not use localhost or 127.0.0.1 as the host ip.

---

## Run

```bash
docker-compose up -d
```

```bash
node index.js
```

Go to: [localhost:3000](http://localhost:3000)

---

## Kafka Manager

[localhost:9000](http://localhost:9000)

1. Cluster: Add Cluster
   1. Cluster Name: anything
   1. Cluster Zookeeper Hosts: `zoo:2181`
   1. Check:
      - Enable JMX Polling (Set JMX_PORT env variable before starting kafka server)
      - Poll consumer information (Not recommended for large # of consumers if ZK is used for offsets tracking on older Kafka versions)

---

## Clean up

```bash
docker-compose up -d
```
