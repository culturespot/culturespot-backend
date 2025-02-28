package com.culturespot.culturespotdomain.auth.controller;

import com.culturespot.culturespotdomain.auth.dto.SignInRequestDto;
import com.culturespot.culturespotdomain.auth.dto.SignUpRequestDto;
import com.culturespot.culturespotdomain.auth.dto.UserResponseDto;
import com.culturespot.culturespotdomain.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
//  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequestDto request) {
    userService.signUp(request);
    return ResponseEntity.ok("회원가입이 완료되었습니다.");
  }

  @PostMapping("/signin")
  public ResponseEntity<UserResponseDto> signIn(@Valid @RequestBody SignInRequestDto request) {
    UserResponseDto response = userService.signIn(request);
    return ResponseEntity.ok(response);
  }
}
