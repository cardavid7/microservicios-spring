package com.cardavid.ms.item.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemCoreInitData {

	@Value("${spring.application.name}")
	private String coreName;
	
	@Value("${spring.application.version}")
	private String coreVersion;
	
	public String getCoreName() {
		return coreName;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}

	public String getCoreVersion() {
		return coreVersion;
	}

	public void setCoreVersion(String coreVersion) {
		this.coreVersion = coreVersion;
	}

}
