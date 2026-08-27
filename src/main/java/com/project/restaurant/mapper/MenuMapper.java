package com.project.restaurant.mapper;

import com.project.restaurant.dto.request.CreateMenuRequest;
import com.project.restaurant.dto.request.UpdateMenuRequest;
import com.project.restaurant.dto.response.MenuResponse;
import com.project.restaurant.dto.response.SimpleMenuResponse;
import com.project.restaurant.entity.Menu;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isAvailable", ignore = true)
    Menu toEntity(CreateMenuRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromRequest(UpdateMenuRequest request, @MappingTarget Menu menu);

    MenuResponse toResponse(Menu menu);
    SimpleMenuResponse toSimpleResponse(Menu menu);
}
