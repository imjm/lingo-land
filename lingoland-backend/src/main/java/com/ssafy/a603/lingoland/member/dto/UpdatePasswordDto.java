package com.ssafy.a603.lingoland.member.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdatePasswordDto(
        @Length(max = 25) @NotBlank String password,
        @Length(max = 25) @NotBlank String checkedPassword) {
}
