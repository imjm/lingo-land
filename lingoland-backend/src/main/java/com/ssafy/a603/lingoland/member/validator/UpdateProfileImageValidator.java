package com.ssafy.a603.lingoland.member.validator;

import com.ssafy.a603.lingoland.member.dto.UpdateProfileImageDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Base64;

@Component
public class UpdateProfileImageValidator implements Validator {

    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    @Override
    public boolean supports(Class<?> clazz) {
        return UpdateProfileImageDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateProfileImageDto dto = (UpdateProfileImageDto) target;
        String profileImage = dto.profileImage();
        if(profileImage == null || profileImage.isEmpty()) {
            errors.rejectValue("profileImage", "빈 이미지 파일입니다.");
            return;
        }
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(profileImage);
            if(decodedBytes.length > MAX_IMAGE_SIZE) {
                errors.rejectValue("profileImage", "이미지 파일 크기가 5MB를 초과했습니다.");
            }
        } catch (IllegalArgumentException e) {
            errors.rejectValue("profileImage", "유효하지 않은 인코딩 파일입니다.");
        }
    }
}
