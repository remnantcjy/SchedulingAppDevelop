package org.example.schedulingappdevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotBlank
    private String comment;

    @NotBlank
    private String password;
}
