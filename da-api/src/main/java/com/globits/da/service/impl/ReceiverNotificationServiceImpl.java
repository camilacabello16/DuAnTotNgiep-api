package com.globits.da.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Notification;
import com.globits.da.domain.ReceiverNotification;
import com.globits.da.dto.ReceiverNotificationDto;
import com.globits.da.repository.NotificationRepository;
import com.globits.da.repository.ReceiverNotificationRepository;
import com.globits.da.service.ReceiverNotificationService;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.UserService;

@Service
public class ReceiverNotificationServiceImpl extends GenericServiceImpl<ReceiverNotification, UUID>
		implements ReceiverNotificationService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private ReceiverNotificationRepository receiverNotificationRepository;
	@Autowired
	private UserService userService;

	@Override
	public ReceiverNotificationDto saveOrUpdate(UUID id, ReceiverNotificationDto dto) {
		if (dto != null) {
			ReceiverNotification entity = null;
			User user = null;
			Notification notification = null;
			if (id != null) {
				entity = receiverNotificationRepository.getOne(id);
			}
			if (entity == null) {
				entity = new ReceiverNotification();
			}
			entity.setIsRead(dto.getIsRead());
			if (dto.getUserDto() != null && dto.getUserDto().getId() != null) {
				user = userRepository.findById(dto.getUserDto().getId()).get();
				entity.setUser(user);
			}
			if (dto.getNotificationDto() != null && dto.getNotificationDto().getId() != null) {
				notification = notificationRepository.findById(dto.getNotificationDto().getId()).get();
				entity.setNotification(notification);
			}
			entity = receiverNotificationRepository.save(entity);
			if (entity != null) {
				return new ReceiverNotificationDto(entity);
			}
		}
		return null;
	}

	@Override
	public Boolean deleteNotifi(UUID id) {
		if(id!=null) {
			ReceiverNotification receiverNotification = receiverNotificationRepository.findById(id).orElse(null);
			if(receiverNotification!=null) {
				receiverNotificationRepository.deleteById(id);
				return true;
			}
		}
		return null;
	}

	@Override
	public List<ReceiverNotificationDto> getListNotificationByUser() {
		UserDto userDto = userService.getCurrentUser();
		List<ReceiverNotificationDto> receiverNotificationDtos = new ArrayList<ReceiverNotificationDto>();
		List<ReceiverNotification> receiverNotifications = receiverNotificationRepository.findAll();
		for (ReceiverNotification receiverNotification : receiverNotifications) {
			if (receiverNotification.getUser()!=null && receiverNotification.getUser().getId() == userDto.getId()) {
				ReceiverNotificationDto receiverNotificationDto = new ReceiverNotificationDto(receiverNotification);
				receiverNotificationDtos.add(receiverNotificationDto);
			}
		}
		return receiverNotificationDtos;
	}

}
