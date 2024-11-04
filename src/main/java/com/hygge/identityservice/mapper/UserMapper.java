package com.hygge.identityservice.mapper;

import com.hygge.identityservice.dto.request.UserCreationRequest;
import com.hygge.identityservice.dto.request.UserUpdateRequest;
import com.hygge.identityservice.dto.response.UserResponse;
import com.hygge.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}