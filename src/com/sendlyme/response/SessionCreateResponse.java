package com.sendlyme.response;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sendlyme.modals.SessionCreateModal;

@Component (value = SendlyBeanConstants.COMPONENT_CREATE_RESPONSE)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonTypeName
@XmlRootElement (name = SendlyBeanConstants.COMPONENT_CREATE_RESPONSE)
public class SessionCreateResponse extends BaseResponse {
	
	@JsonProperty("data")
	private SessionCreateModal sessionCreateModal;
	
	public SessionCreateModal getSessionCreateModal() {
		return sessionCreateModal;
	}
	public void setSessionCreateModal(SessionCreateModal sessionCreateModal) {
		this.sessionCreateModal = sessionCreateModal;
	}

}
