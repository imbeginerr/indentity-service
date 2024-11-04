package com.hygge.identityservice.mapper;

import com.hygge.identityservice.dto.request.PermissionRequest;
import com.hygge.identityservice.dto.response.PermissionResponse;
import com.hygge.identityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
