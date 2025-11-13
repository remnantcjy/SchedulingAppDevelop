package org.example.schedulingappdevelop.user.dto;

import lombok.Getter;

@Getter
public class CreateUserResponse {

    private final Long id;
    private final String name;
    private final String email;

    public CreateUserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
