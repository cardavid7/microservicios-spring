package com.cardavid.ms.gatewayserver.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayServerCoreInitData {

	@Value("${spring.application.name}")
	private String coreName;
	
	@Value("${spring.application.version}")
	private String coreVersion;

	public String getCoreName() {
		return coreName;
	}

	public String getCoreVersion() {
		return coreVersion;
	}

	public void setCoreVersion(String coreVersion) {
		this.coreVersion = coreVersion;
	}

	public void setCoreName(String coreName) {
		this.coreName = coreName;
	}
}
