package com.culturespot.culturespotdomain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto (

  @NotBlank(message = "이메일 주소를 입력해주세요")
  @Email(message = "유효한 이메일 주소를 입력해주세요")
  String email,

  @NotBlank(message = "비밀번호를 입력해주세요")
  @Size(min = 8, message = "비밀번호 길이가 맞지 않습니다.")
  String password,

  @NotBlank(message = "닉네임을 입력해주세요")
  @Size(min = 2, max = 12, message = "닉네임은 2~12자 사이여야 합니다.")
  String username
) {}