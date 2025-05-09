package com.example.karaoke.booked_room_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookedRoomServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookedRoomServiceApplication.class, args);
	}

}

