package com.globits.da.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.globits.security.dto.UserDto;
import com.globits.security.service.UserService;

@RestController
@RequestMapping("/oauth/register")
public class RestRegisterAccountController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UserDto> save(@RequestBody UserDto dto) {
		UserDto result = userService.save(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value ="/username/{username}",  method = RequestMethod.GET)
	public ResponseEntity<Boolean> findUserByUserName(@PathVariable(name = "username") String username) {
		Boolean result = false;
		UserDto userDto = userService.findByUsername(username);
		if(userDto!=null) {
			result = true;
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value ="/email",  method = RequestMethod.POST)
	public ResponseEntity<Boolean> findUserByEmail(@RequestParam(name = "email") String email) {
		Boolean result = false;
		UserDto userDto = userService.findByEmail(email);
		if(userDto!=null) {
			result = true;
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
