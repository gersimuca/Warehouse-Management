package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.AuthenticationRequest;
import com.gersimuca.Warehouse.Management.dto.RegisterRequest;
import com.gersimuca.Warehouse.Management.enumeration.Role;
import com.gersimuca.Warehouse.Management.model.Token;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.TokenRepository;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.security.provider.JwtService;
import com.gersimuca.Warehouse.Management.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public Map<String, String> register(RegisterRequest request) {
        Map<String, String> data = new HashMap<>();
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.CLIENT )
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        Token token = TokenUtil.saveUserToken(savedUser, jwtToken);
        tokenRepository.save(token);

        data.put("access_token", jwtToken);
        return data;
    }

    public Map<String, String> authenticate(AuthenticationRequest request) {
        Map<String, String> data = new HashMap<>();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        List<Token> validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        TokenUtil.revokeAllUserToken(validUserToken);
        tokenRepository.saveAll(validUserToken);

        Token token = TokenUtil.saveUserToken(user, jwtToken);
        tokenRepository.save(token);

        data.put("access_token", jwtToken);
        return data;
    }
}
