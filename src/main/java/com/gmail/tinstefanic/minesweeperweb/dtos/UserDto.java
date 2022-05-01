package com.gmail.tinstefanic.minesweeperweb.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    public UserDto() {}

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
