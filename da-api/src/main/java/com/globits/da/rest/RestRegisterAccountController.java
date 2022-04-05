package com.globits.da.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.globits.security.dto.UserDto;
import com.globits.security.service.UserService;

@RestController
@RequestMapping("/api/register/account")
public class RestRegisterAccountController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserDto> save(@RequestBody UserDto dto) {
		UserDto result = userService.save(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value ="/{username}",  method = RequestMethod.POST)
	public ResponseEntity<UserDto> findByUserName(@PathVariable(name = "username") String username) {
		UserDto result = userService.findByUsername(username);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value ="/{email}",  method = RequestMethod.POST)
	public ResponseEntity<UserDto> findByEmail(@PathVariable(name = "email") String email) {
		UserDto result = userService.findByEmail(email);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
