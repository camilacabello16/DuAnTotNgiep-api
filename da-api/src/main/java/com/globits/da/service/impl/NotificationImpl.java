package com.globits.da.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Notification;
import com.globits.da.dto.NotificationDto;
import com.globits.da.repository.NotificationRepository;
import com.globits.da.service.NotificationService;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
@Transactional
@Service
public class NotificationImpl extends GenericServiceImpl<Notification, UUID> implements NotificationService {
	
	@Autowired
	private NotificationRepository aNotificationRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public NotificationDto saveOrUpdate(UUID id, NotificationDto dto) {
		if(dto!=null) {
			Notification notification = null;
			User user = null;
			if(id!=null) {
				notification = aNotificationRepository.getOne(id);
			}
			if(notification == null) {
				notification = new Notification();
			}
			notification.setType(dto.getType());
			notification.setContent(dto.getContent());
			notification = aNotificationRepository.save(notification);
			if(notification!=null) {
				return new NotificationDto(notification);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteNotification(UUID id) {
		if(id!=null) {
			aNotificationRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<NotificationDto> getListNotification() {
		List<NotificationDto> notificationDtos = new ArrayList<NotificationDto>();
		List<Notification> notifications = aNotificationRepository.findAll();
		for(Notification notification:notifications) {
			NotificationDto notificationDto = new  NotificationDto(notification);
			notificationDtos.add(notificationDto);
		}
		return notificationDtos;
	}

}
