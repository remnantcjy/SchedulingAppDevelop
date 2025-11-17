package org.example.schedulingappdevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.cglib.core.Block;

@Getter
public class DeleteCommentRequest {

    @NotBlank
    private String password;
}
