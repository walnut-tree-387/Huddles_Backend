package com.example.Threading.Helpers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public SystemMapper(ModelMapper modelMapper) {
        SystemMapper.modelMapper = modelMapper;
    }

    public static <D, T> D toDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    public static <T, D> T toEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
    public static <D, T> List<D> toDtoList(List<T> entityList, Class<D> dtoClass) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }
    public static <T, D> List<T> toEntityList(List<D> dtoList, Class<T> entityClass) {
        return dtoList.stream()
                .map(dto -> modelMapper.map(dto, entityClass))
                .collect(Collectors.toList());
    }
}
