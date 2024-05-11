package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.UserDto;
import com.gersimuca.Warehouse.Management.dto.mapper.UserDtoMapper;
import com.gersimuca.Warehouse.Management.dto.request.UserRequest;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.TokenRepository;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @TrackExecutionTime
    public Map<String, Object> getAllUsers() {
        try {
            List<User> users = Optional.of(userRepository.findAll())
                    .orElseThrow(() -> new ServiceException("Users not found"));

            List<UserDto> userDtos = users
                    .stream()
                    .map(user -> new UserDtoMapper().apply(user))
                    .toList();

            Map<String, Object> data = new HashMap<>();
            data.put("users", List.of(userDtos));
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting all users: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void createUser(UserRequest userRequest) {
        try {
            userRepository.findByUsername(userRequest.getUsername()).ifPresent(user -> {
                throw new ServiceException("User with this username already exists");
            });

            authenticationService.registerClient(userRequest.getUsername(), userRequest.getPassword());
        } catch (Exception e) {
            log.error("Error occurred while creating user: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public void updateUser(String username, UserRequest userRequest) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ServiceException("User not found"));
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while updating user: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    @TrackExecutionTime
    public void deleteUser(String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ServiceException("User not found"));

            tokenRepository.deleteTokensByUser(user);
            userRepository.delete(user);
        } catch (Exception e) {
            log.error("Error occurred while deleting user: {}", e.getMessage());
            throw e;
        }
    }

    @TrackExecutionTime
    public Map<String, Object> getUserByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ServiceException("User not found"));

            UserDto userDto = new UserDtoMapper().apply(user);

            Map<String, Object> data = new HashMap<>();
            data.put("user", userDto);
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting user by username: {}", e.getMessage());
            throw e;
        }
    }
}
