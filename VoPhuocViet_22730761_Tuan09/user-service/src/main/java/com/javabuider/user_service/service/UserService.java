package com.javabuider.user_service.service;

import com.javabuider.user_service.dto.request.CreateUserRequest;
import com.javabuider.user_service.dto.response.CreateUserResponse;
import com.javabuider.user_service.dto.response.UserDetailResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
public interface UserService {
    CreateUserResponse createUser(CreateUserRequest request);
    UserDetailResponse myInfo(String userId);
    List<UserDetailResponse> getAllUsers();
    String updateAvatar(String userId, MultipartFile file);
}