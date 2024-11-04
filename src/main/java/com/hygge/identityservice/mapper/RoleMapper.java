package com.hygge.identityservice.mapper;

import com.hygge.identityservice.dto.request.RoleRequest;
import com.hygge.identityservice.dto.response.RoleResponse;
import com.hygge.identityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
