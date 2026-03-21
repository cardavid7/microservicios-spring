package com.cardavid.ms.eurekaserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

import com.cardavid.ms.eurekaserver.core.config.EurekaServerCoreInitData;

@EnableEurekaServer
@SpringBootApplication
public class MsEurekaserverCoreApplication {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(MsEurekaserverCoreApplication.class);
		
		ConfigurableApplicationContext context = SpringApplication.run(MsEurekaserverCoreApplication.class, args);
		
		EurekaServerCoreInitData eurekaServerCoreInitData = context.getBean(EurekaServerCoreInitData.class);
		logger.info(String.format("####### %s-(%s) IS UP #######",eurekaServerCoreInitData.getCoreName().toUpperCase(),eurekaServerCoreInitData.getCoreVersion()));
	}

}
