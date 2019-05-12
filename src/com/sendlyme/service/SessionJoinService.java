package com.sendlyme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.sendlyme.modals.SessionJoinModal;
import com.sendlyme.response.SendlyBeanConstants;
import com.sendlyme.response.SessionCreateResponse;
import com.sendlyme.response.SessionJoinResponse;
import com.sendlyme.utils.DataUtil;
import com.sendlyme.utils.RedisUtil;

@RefreshScope
@Service(value = SendlyBeanConstants.SERVICE_JOIN)
@Scope(value = "request", proxyMode=ScopedProxyMode.INTERFACES)
public class SessionJoinService {

	@Autowired
	private ApplicationContext context;
	
	
	public SessionJoinResponse getJoinResponse(String ip, String sessionId) {
		SessionJoinResponse response = context.getBean(SendlyBeanConstants.COMPONENT_JOIN_RESPONSE,SessionJoinResponse.class);
		
		if(DataUtil.ipValidate(ip))
		{
			SessionJoinModal session = new SessionJoinModal();
			String userId = RedisUtil.getInstance().joinSession(sessionId,ip);
			if(!userId.equalsIgnoreCase(""))
			{
				session.setUserId(userId);
				response.setSessionJoinModal(session);
				response.setStatus(true);
			}
		}
		return response;
		
	}
	
}
