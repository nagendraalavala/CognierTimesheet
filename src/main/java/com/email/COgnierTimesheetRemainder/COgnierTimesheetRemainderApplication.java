package com.email.COgnierTimesheetRemainder;

import com.cognier.timesheet.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class COgnierTimesheetRemainderApplication {

	@Autowired
	private EmailService service;

	public static void main(String[] args) {
		SpringApplication.run(COgnierTimesheetRemainderApplication.class, args);
	}

}
