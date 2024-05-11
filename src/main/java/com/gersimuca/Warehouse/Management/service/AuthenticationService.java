package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.request.AuthenticationRequest;
import com.gersimuca.Warehouse.Management.dto.request.RegisterRequest;
import com.gersimuca.Warehouse.Management.enumeration.Role;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.model.Token;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.TokenRepository;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.security.provider.JwtService;
import com.gersimuca.Warehouse.Management.util.TokenUtil;
import com.gersimuca.Warehouse.Management.util.UserUtil;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @TrackExecutionTime
    public Map<String, String> register(RegisterRequest request) {
        Map<String, String> data = new HashMap<>();

        Optional<User> userExist = userRepository.findByUsername(request.getUsername());
        if(userExist.isPresent()) {
            logger.warn("User {} already exists", request.getUsername());
            throw new ServiceException("User already exist");
        }

        User user = UserUtil.saveUser(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getRole() != null ? request.getRole() : Role.CLIENT);

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        Token token = TokenUtil.saveUserToken(savedUser, jwtToken);
        tokenRepository.save(token);

        data.put("access_token", jwtToken);
        logger.info("User {} registered successfully", request.getUsername());
        return data;
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void registerClient(String username, String password) {
        User user = UserUtil.saveUser(username, passwordEncoder.encode(password), Role.CLIENT);
        User savedUser = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        Token token = TokenUtil.saveUserToken(savedUser, jwtToken);
        tokenRepository.save(token);

        logger.info("Client {} registered successfully", username);
    }

    @TrackExecutionTime
    public Map<String, String> authenticate(AuthenticationRequest request) {
        Map<String, String> data = new HashMap<>();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        List<Token> validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        TokenUtil.revokeAllUserToken(validUserToken);

        tokenRepository.saveAll(validUserToken);

        Token token = TokenUtil.saveUserToken(user, jwtToken);
        tokenRepository.save(token);

        data.put("access_token", jwtToken);
        logger.info("User {} authenticated successfully", request.getUsername());
        return data;
    }
}
