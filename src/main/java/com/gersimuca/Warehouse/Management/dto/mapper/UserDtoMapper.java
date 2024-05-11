package com.gersimuca.Warehouse.Management.dto.mapper;

import com.gersimuca.Warehouse.Management.dto.UserDto;
import com.gersimuca.Warehouse.Management.model.User;

import java.util.Base64;
import java.util.function.Function;

public class UserDtoMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getUsername(),
                user.getRole()
        );
    }

    private static String decodeBase64(String passwordEncoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(passwordEncoded);
        return new String(decodedBytes);
    }
}
