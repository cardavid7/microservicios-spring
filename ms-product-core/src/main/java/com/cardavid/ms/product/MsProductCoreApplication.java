package com.cardavid.ms.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.cardavid.ms.product.core.config.ProductCoreInitData;

@SpringBootApplication
public class MsProductCoreApplication {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(MsProductCoreApplication.class);

		ConfigurableApplicationContext context = SpringApplication.run(MsProductCoreApplication.class, args);
		
		ProductCoreInitData productCoreInitData = context.getBean(ProductCoreInitData.class);
		logger.info(String.format("####### %s-(%s) IS UP #######",productCoreInitData.getCoreName().toUpperCase(),productCoreInitData.getCoreVersion()));
	}

}
