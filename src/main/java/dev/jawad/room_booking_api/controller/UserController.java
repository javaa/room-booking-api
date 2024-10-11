package dev.jawad.room_booking_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import dev.jawad.room_booking_api.errors.Errors;
import dev.jawad.room_booking_api.exception.ApplicationException;
import dev.jawad.room_booking_api.model.User;
import dev.jawad.room_booking_api.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<Page<User>> getAllUsers(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication != null && authentication.getAuthorities().stream()
              .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {

          Page<User> users = userRepository.findAll(PageRequest.of(page, size));
          return ResponseEntity.ok(users);
      } else {
          throw new ApplicationException(Errors.NO_PERMISSION);
      }
    }
}
