package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.ReceiverNotification;
import com.globits.security.dto.UserDto;

public class ReceiverNotificationDto extends BaseObjectDto {
	private NotificationDto notificationDto;
	private UserDto userDto;
	private Integer isRead;
	public NotificationDto getNotificationDto() {
		return notificationDto;
	}
	public void setNotificationDto(NotificationDto notificationDto) {
		this.notificationDto = notificationDto;
	}
	
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public ReceiverNotificationDto() {}
	
	public ReceiverNotificationDto(ReceiverNotification entity) {
		if(entity.getUser()!=null &&entity.getUser().getId()!=null) {
			UserDto userDto = new UserDto(entity.getUser());
			this.userDto = userDto;
		}
		if(entity.getNotification()!=null && entity.getNotification().getId()!=null) {
			NotificationDto notificationDto = new NotificationDto(entity.getNotification());
			this.notificationDto = notificationDto;
		}
		this.isRead = entity.getIsRead();
	}
}
