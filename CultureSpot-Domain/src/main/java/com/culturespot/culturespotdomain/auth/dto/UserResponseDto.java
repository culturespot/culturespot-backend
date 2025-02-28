package com.culturespot.culturespotdomain.auth.dto;

public record UserResponseDto (
  String email,
  String username
) {}