package com.javabuider.user_service.service.impl;

import com.javabuider.user_service.client.MediaClient;
import com.javabuider.user_service.common.RoleType;
import com.javabuider.user_service.dto.request.CreateUserRequest;
import com.javabuider.user_service.dto.response.CreateUserResponse;
import com.javabuider.user_service.dto.response.FileResponse;
import com.javabuider.user_service.dto.response.UserDetailResponse;
import com.javabuider.user_service.entity.Role;
import com.javabuider.user_service.entity.User;
import com.javabuider.user_service.exception.ErrorCode;
import com.javabuider.user_service.exception.UserServiceException;
import com.javabuider.user_service.mapper.UserMapper;
import com.javabuider.user_service.repository.UserRepository;
import com.javabuider.user_service.service.RoleService;
import com.javabuider.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER-SERVICE")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final MediaClient mediaClient;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        Role role = roleService.createRole(RoleType.CUSTOMER.name());
        user.addRole(role);
        try {
            userRepository.save(user);
        }catch (DataIntegrityViolationException exception) {
            log.error("User already exists");
            throw new UserServiceException(ErrorCode.USER_ALREADY_EXISTS);
        }
        return userMapper.toCreateUserResponse(user);
    }

    @Override
    public UserDetailResponse myInfo(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserDetailResponse(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDetailResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDetailResponse)
                .toList();
    }

    @Override
    public String updateAvatar(String userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserServiceException(ErrorCode.USER_NOT_FOUND));

        try {
            FileResponse response = mediaClient.uploadFile(file).data();
            String avatarUrl = response.url();
            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);
            return avatarUrl;
        } catch (Exception e) {
            log.error("Failed to upload file", e);
            throw new UserServiceException(ErrorCode.MEDIA_UPLOAD_FAILED);
        }
    }

}