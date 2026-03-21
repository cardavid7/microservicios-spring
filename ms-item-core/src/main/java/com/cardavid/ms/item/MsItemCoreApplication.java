package com.cardavid.ms.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import com.cardavid.ms.item.core.config.ItemCoreInitData;

@EnableFeignClients
@SpringBootApplication
public class MsItemCoreApplication {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(MsItemCoreApplication.class);

		ConfigurableApplicationContext context = SpringApplication.run(MsItemCoreApplication.class, args);
		
		ItemCoreInitData itemCoreInitData = context.getBean(ItemCoreInitData.class);
		logger.info(String.format("####### %s-(%s) IS UP #######",itemCoreInitData.getCoreName().toUpperCase(),itemCoreInitData.getCoreVersion()));
	}

}
