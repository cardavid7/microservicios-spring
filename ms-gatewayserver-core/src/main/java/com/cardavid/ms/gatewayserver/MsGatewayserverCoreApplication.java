package com.cardavid.ms.gatewayserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cardavid.ms.gatewayserver.core.config.GateWayServerCoreInitData;

@SpringBootApplication
public class MsGatewayserverCoreApplication {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(MsGatewayserverCoreApplication.class);
				
		ConfigurableApplicationContext context = SpringApplication.run(MsGatewayserverCoreApplication.class, args);
		
		GateWayServerCoreInitData gateWayServerCoreInitData = context.getBean(GateWayServerCoreInitData.class);
		logger.info(String.format("####### %s-(%s) IS UP #######",gateWayServerCoreInitData.getCoreName().toUpperCase(),gateWayServerCoreInitData.getCoreVersion()));
		//logger.info(String.format("####### %s(%s)-(%s) IS UP #######",gateWayServerCoreInitData.getCoreName().toUpperCase(),"8090",gateWayServerCoreInitData.getCoreVersion()));
	}

}
