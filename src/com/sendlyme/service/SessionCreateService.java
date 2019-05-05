package com.sendlyme.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.sendlyme.modals.SessionCreateModal;
import com.sendlyme.response.SendlyBeanConstants;
import com.sendlyme.response.SessionCreateResponse;
import com.sendlyme.utils.DataUtil;
import com.sendlyme.utils.RedisUtil;

@RefreshScope
@Service(value = SendlyBeanConstants.SERVICE_CREATE)
@Scope(value = "request", proxyMode=ScopedProxyMode.INTERFACES)
public class SessionCreateService {
	
	@Autowired
	private ApplicationContext context;
	
	public SessionCreateResponse getCreateResponse(String ip) {
		
		SessionCreateResponse response = context.getBean(SendlyBeanConstants.COMPONENT_CREATE_RESPONSE,SessionCreateResponse.class);
		
		if(DataUtil.ipValidate(ip))
		{
			String sessionId = UUID.randomUUID().toString();
			String userId = UUID.randomUUID().toString();
			
			SessionCreateModal session = new SessionCreateModal();
			session.setSessionId(sessionId);
			session.setUserId(userId);
			
			RedisUtil.getInstance().createSession(sessionId, userId);
			response.setSessionCreateModal(session);
			response.setStatus(true);
		}
		return response;
		
	}
}
