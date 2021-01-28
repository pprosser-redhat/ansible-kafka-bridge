## Usage

* create an application.properties file in the following format:

```properties
    messaging.broker.url=<<<bootstrap url>>>>
``

oc create configmap tower-bridge-config --from-file=application.properties

apiVersion: kafka.strimzi.io/v1beta1
kind: KafkaTopic
metadata:
  name: ansible-message
  namespace: amqstreams
  labels:
    strimzi.io/cluster: tower-cluster
spec:
  config:
    retention.ms: 3600000
    segment.bytes: 1073741824
  partitions: 10
  replicas: 1
  topicName: ansible-message

apiVersion: kafka.strimzi.io/v1beta1
kind: Kafka
metadata:
  name: tower-cluster
  namespace: amqstreams
spec:
  entityOperator:
    topicOperator: {}
    userOperator: {}
  kafka:
    config:
      inter.broker.protocol.version: '2.6'
      log.message.format.version: '2.6'
      offsets.topic.replication.factor: 1
      transaction.state.log.min.isr: 1
      transaction.state.log.replication.factor: 1
    listeners:
      - name: plain
        port: 9092
        tls: false
        type: internal
      - name: tls
        port: 9093
        tls: true
        type: internal
    replicas: 1
    storage:
      deleteClaim: true
      size: 5Gi
      type: persistent-claim
    version: 2.6.0
  zookeeper:
    replicas: 1
    storage:
      deleteClaim: true
      size: 5Gi
      type: persistent-claim