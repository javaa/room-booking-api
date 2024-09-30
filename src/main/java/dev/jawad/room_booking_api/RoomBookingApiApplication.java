package dev.jawad.room_booking_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.jawad.room_booking_api")
public class RoomBookingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomBookingApiApplication.class, args);
	}

}
