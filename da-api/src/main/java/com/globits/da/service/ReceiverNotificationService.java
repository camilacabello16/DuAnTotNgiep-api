package com.globits.da.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.ReceiverNotification;
import com.globits.da.dto.ReceiverNotificationDto;

@Service
public interface ReceiverNotificationService extends GenericService<ReceiverNotification, UUID>  {
	
	public ReceiverNotificationDto saveOrUpdate(UUID id,ReceiverNotificationDto dto);
	public Boolean deleteNotifi(UUID id);
	public List<ReceiverNotificationDto> getListNotificationByUser();
}
