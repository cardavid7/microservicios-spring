package com.cardavid7.ms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cardavid7.ms.security.core.config.SecurityCoreInitData;

@SpringBootApplication
public class MsSecurityCoreApplication {

	public static void main(String[] args) {

		final Logger logger = LoggerFactory.getLogger(MsSecurityCoreApplication.class);

		ConfigurableApplicationContext context = SpringApplication.run(MsSecurityCoreApplication.class, args);

		SecurityCoreInitData securityCoreInitData = context.getBean(SecurityCoreInitData.class);
		logger.info(String.format("####### %s-(%s) IS UP #######", securityCoreInitData.getCoreName().toUpperCase(),
				securityCoreInitData.getCoreVersion()));
	}

}
