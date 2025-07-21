package com.bell.springstub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotBlank(message = "Логин не должен быть пустым")
    private String login;
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
    @NotBlank(message = "Адресс почты не должен быть пустым")
    private String email;
    private Date date;
}