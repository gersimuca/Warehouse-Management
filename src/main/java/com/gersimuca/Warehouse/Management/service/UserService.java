package com.gersimuca.Warehouse.Management.service;

import com.gersimuca.Warehouse.Management.dto.UserDto;
import com.gersimuca.Warehouse.Management.dto.mapper.UserDtoMapper;
import com.gersimuca.Warehouse.Management.dto.request.ChangePasswordRequest;
import com.gersimuca.Warehouse.Management.dto.request.UserRequest;
import com.gersimuca.Warehouse.Management.exception.ServiceException;
import com.gersimuca.Warehouse.Management.exception.TruckException;
import com.gersimuca.Warehouse.Management.exception.UserException;
import com.gersimuca.Warehouse.Management.model.User;
import com.gersimuca.Warehouse.Management.repository.TokenRepository;
import com.gersimuca.Warehouse.Management.repository.UserRepository;
import com.gersimuca.Warehouse.Management.util.metrics.TrackExecutionTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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
                    .orElseThrow(() -> {
                        String message = "Users not found";
                        Throwable cause = new UserException("Users not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "Users not found in records";
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });

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
                String message = "User is already registered";
                Throwable cause = new UserException("User is already registered");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "User " + userRequest.getUsername() + " is already registered";
                boolean trace = true;
                throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
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
                    .orElseThrow(() -> {
                        String message = "User not found";
                        Throwable cause = new UserException("User not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "User " + userRequest.getUsername() + " not  found!";
                        boolean trace = true;
                        throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });
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
                    .orElseThrow(() -> {
                        String message = "User not found";
                        Throwable cause = new UserException("User not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "User " + username + " not  found!";
                        boolean trace = true;
                        throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });

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
                    .orElseThrow(() -> {
                        String message = "User not found";
                        Throwable cause = new UserException("User not found");
                        HttpStatus status = HttpStatus.BAD_REQUEST;
                        String errorDetailMessage = "User " + username + " not  found!";
                        boolean trace = true;
                        return new ServiceException(message, cause, status, null, errorDetailMessage, trace);
                    });

            UserDto userDto = new UserDtoMapper().apply(user);

            Map<String, Object> data = new HashMap<>();
            data.put("user", userDto);
            return data;
        } catch (Exception e) {
            log.error("Error occurred while getting user by username: {}", e.getMessage());
            throw e;
        }
    }



    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        try {
            User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                String message = "Current password didn't match up";
                Throwable cause = new UserException("Current password didn't match up");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "User " + user.getUsername() + " password didn't match up, please enter the correct one";
                boolean trace = true;
                throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
            }

            if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
                String message = "New password didn't match up with the confirmation password";
                Throwable cause = new UserException("New password didn't match up with the confirmation password");
                HttpStatus status = HttpStatus.BAD_REQUEST;
                String errorDetailMessage = "User " + user.getUsername() + " new password didn't match up with the confirmation password, please enter the correct one";
                boolean trace = true;
                throw new ServiceException(message, cause, status, null, errorDetailMessage, trace);
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while changing password: {}", e.getMessage());
            throw e;
        }
    }
}
