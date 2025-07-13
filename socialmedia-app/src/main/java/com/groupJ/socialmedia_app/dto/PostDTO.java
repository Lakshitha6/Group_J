package com.groupJ.socialmedia_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO {
    @NotBlank(message = "Post content cannot be empty")
    @Size(max = 500,message = "Cannot exceed 500 characters")
    private String content;
}
