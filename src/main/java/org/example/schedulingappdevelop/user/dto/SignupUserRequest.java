package org.example.schedulingappdevelop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupUserRequest {

    // 이름은 4자 이내
    @NotBlank @Size(max = 4, message = "이름은 {max}자 이하이어야 합니다.")
    private String name;

    @Email
    private String email;

    // 비밀번호는 4자 이상
    @NotBlank @Size(min = 4, message = "비밀번호는 {min}자 이상이어야 합니다.")
    private String password;
}
