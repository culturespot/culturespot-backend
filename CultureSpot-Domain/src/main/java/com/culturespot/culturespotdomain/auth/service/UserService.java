package com.culturespot.culturespotdomain.auth.service;

import com.culturespot.culturespotdomain.auth.dto.SignInRequestDto;
import com.culturespot.culturespotdomain.auth.dto.SignUpRequestDto;
import com.culturespot.culturespotdomain.auth.dto.UserResponseDto;

public interface UserService {
  void signUp(SignUpRequestDto request);
  UserResponseDto signIn(SignInRequestDto request);
}
