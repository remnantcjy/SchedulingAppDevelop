package org.example.schedulingappdevelop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    // 이름만 변경
    @NotBlank @Size(max = 4, message = "이름은 {max}자 이하이어야 합니다.")
    private String name;

    @NotBlank
    private String password;
}
