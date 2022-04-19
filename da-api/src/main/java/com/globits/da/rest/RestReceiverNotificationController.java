package com.globits.da.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.globits.da.dto.ReceiverNotificationDto;
import com.globits.da.service.ReceiverNotificationService;
@RestController
@RequestMapping("/api/receiver-notification")
public class RestReceiverNotificationController {
	@Autowired
	ReceiverNotificationService receiverNotificationService;
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ReceiverNotificationDto> save(@RequestBody ReceiverNotificationDto dto) {
		ReceiverNotificationDto result = receiverNotificationService.saveOrUpdate(null, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ReceiverNotificationDto> update(@RequestBody ReceiverNotificationDto dto,@PathVariable("id") UUID id) {
		ReceiverNotificationDto result = receiverNotificationService.saveOrUpdate(id, dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ReceiverNotificationDto>> getListNotificationByUserId() {
		List<ReceiverNotificationDto> result = receiverNotificationService.getListNotificationByUser();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
