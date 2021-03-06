# Spark Streaming + Kafka

## Setup

`docker-compose.yml`: Line 24. Set `KAFKA_ADVERTISED_HOST_NAME: # <HOST_IP>`

Do not use localhost or 127.0.0.1 as the host ip.

### Setup Node

```bash
yarn
```

---

## Run

Start a cluster:

```bash
docker-compose up -d
```

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
