package com.redhat.consulting.config;

import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

import com.sun.istack.Pool;


@Configuration
public class AMQConfiguration {

	@Value("${amq7.remote.uri}")
	private String remoteUri;
	
	@Value("amq7.username")
	private String username;

	@Value("amq7.password")
	private String password;

	
	
	PooledConnectionFactory pool = new PooledConnectionFactory();
	
	
	public org.apache.qpid.jms.JmsConnectionFactory jmsConnectionFactory() {
		JmsConnectionFactory jmsCf = new JmsConnectionFactory();
		jmsCf.setRemoteURI("failover:(amqp://127.0.0.1:61616,amqp://127.0.0.1:61617,amqp://127.0.0.1:61618,amqp://127.0.0.1:61619)?initialReconnectDelay=100");
		jmsCf.setUsername("admin");
		jmsCf.setPassword("1qaz@WSX");
		
		return jmsCf;
			
	}
	
	@Bean
	public org.springframework.jms.connection.CachingConnectionFactory jmsCachingConnectionFactory() {
		
		CachingConnectionFactory jmsCCF = new CachingConnectionFactory();	
		jmsCCF.setTargetConnectionFactory(jmsConnectionFactory());
		
		return jmsCCF;
		
	}
	
	@Bean(name = "jmsConfig")
	public org.apache.camel.component.jms.JmsConfiguration jmsConfig() {
		
		JmsConfiguration jms = new JmsConfiguration();
		jms.setConnectionFactory(jmsConnectionFactory());
		
		return jms;
		
		
	}

	@Bean(name = "amqp")
	public org.apache.camel.component.amqp.AMQPComponent amqp() {
		AMQPComponent amqp = new AMQPComponent(jmsConfig());
		
		return amqp;
				
	}
	
}