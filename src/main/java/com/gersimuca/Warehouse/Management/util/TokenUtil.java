package com.gersimuca.Warehouse.Management.util;

import com.gersimuca.Warehouse.Management.enumeration.TokenType;
import com.gersimuca.Warehouse.Management.model.Token;
import com.gersimuca.Warehouse.Management.model.User;

import java.util.List;

public class TokenUtil {
    public static Token saveUserToken(User user, String jwtToken) {
        return Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
    }

    public static List<Token> revokeAllUserToken(List<Token> validUserTokens){
        if(validUserTokens.isEmpty()) return validUserTokens;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        
        return validUserTokens;
    }
}
