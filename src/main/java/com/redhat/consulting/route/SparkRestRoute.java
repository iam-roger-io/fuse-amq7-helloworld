package com.redhat.consulting.route;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Rogerio L Santos ✡
 *
 */
@Component
public class SparkRestRoute extends RouteBuilder {

	
	@Override
	public void configure() throws Exception {

		
		/**
		 * This web service POST send a message to AMQ7
		 * 
		 * url: http://127.0.0.1:8082/rest/amq7/sendmessage
		 * method:POST  request body: {	"message":"Hello"  } 
		 * 
		 */
		restConfiguration()
				.component("spark-rest").contextPath("rest").port(8082) 
				.bindingMode(RestBindingMode.json)
				.apiProperty("api.description", "Exemplo basico com Fuse com AMQ7");

		rest("/amq7")
				.produces("application/json")
				.post("/sendmessage")
				.to("direct:send-message");
				
		
		/**
		 * This route send 100 messages to the address addressTest each second.
		 */
		from("timer://foo?fixedRate=true&period=1000").autoStartup(true)		
		.process(new Processor() {
			
			public void process(Exchange exchange) throws Exception {
				List<String> list  = new ArrayList<String>();
				String message = "Message number ";
								
				for (int i = 0;i < 100; i++ ) {
					
					list.add(message +i);
				
				}
				exchange.getIn().setBody(list);
			}
		})
		
		.split(body()).parallelProcessing().threads()		
		.to("direct:send-message");
		
		

		/**
		 * This route put a message in a address called addressTest
		 */
		from("direct:send-message")
		.to("amqp:addressTest");
		
		
		/**
		 * The address has 2 queues: foo1 and foo2. 
		 * This route receive the messages from queue foo2
		 */
		from("amqp:foo2").autoStartup(true)
		.log("Recebido pela queue foo2 ${body}");
		

		/**
		 * The address has 2 queues: foo1 and foo2. 
		 * This route receive the messages from queue foo2
		 */
		from("amqp:foo1").autoStartup(true)
		.log("RECEBIDA PELA queue foo1 ${body}");
		
	}

}
