package com.sendlyme.response;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.sendlyme.modals.SessionJoinModal;

@Component (value = SendlyBeanConstants.COMPONENT_JOIN_RESPONSE)
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@JsonTypeName
@XmlRootElement (name = SendlyBeanConstants.COMPONENT_JOIN_RESPONSE)
public class SessionJoinResponse extends BaseResponse{
	
	@JsonProperty("data")
	SessionJoinModal sessionJoinModal;
	
	public SessionJoinModal getSessionJoinModal() {
		return sessionJoinModal;
	}

	public void setSessionJoinModal(SessionJoinModal sessionJoinModal) {
		this.sessionJoinModal = sessionJoinModal;
	}


}
