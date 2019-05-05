package com.sendlyme.response;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Component (value = SendlyBeanConstants.COMPONENT_BASE_RESPONSE)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonTypeName
@XmlRootElement (name = SendlyBeanConstants.COMPONENT_BASE_RESPONSE)
public class BaseResponse {

	 private boolean status;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	 
	 
}
