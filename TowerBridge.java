// camel-k: language=java open-api=openapi.yaml dependency=camel-rest-openapi dependency=camel-kafka
// camel-k: trait=service.auto=true trait=service.node-port=false

// camel-k: configmap=tower-bridge-config

// Example using curl
// curl --header "Content-Type: application/json" --request POST \--data '{"name":"Phil","surname":"Prosser"}' http://tower-bridge-amqstreams.apps.coffee.demolab.local/process
import org.apache.camel.builder.RouteBuilder;

public class TowerBridge extends RouteBuilder {
  @Override
  public void configure() throws Exception {

      // Receive messaage from Ansible Tower:
      from("direct:process")
        .routeId("Tower Bridge")
        .log("${body}")
        .to("kafka:ansible-message?brokers={{kafka.bootstrap.address}}");
  }
}
