package com.globits.da.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.dto.NotificationDto;
import com.globits.da.service.NotificationService;
@RestController
@RequestMapping("/api/notification")
public class RestNotificationController {
	@Autowired
	NotificationService notificationService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<NotificationDto> save(@RequestBody NotificationDto dto) {
		NotificationDto result = notificationService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<NotificationDto> update(@RequestBody NotificationDto dto,@PathVariable("id") UUID id) {
		NotificationDto result = notificationService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
//	@RequestMapping(value = "/search", method = RequestMethod.POST)
//	public ResponseEntity<Page<NotificationDto>> search(@RequestBody SearchDto dto) {
//		Page<NotificationDto> result = notificationService.searchByPage(dto);
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		Boolean result = notificationService.deleteNotification(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
