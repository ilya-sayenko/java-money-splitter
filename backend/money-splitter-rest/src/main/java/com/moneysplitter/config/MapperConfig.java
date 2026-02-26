package com.moneysplitter.config;

import org.mapstruct.MappingConstants;
import org.mapstruct.extensions.spring.SpringMapperConfig;

@org.mapstruct.MapperConfig(componentModel = MappingConstants.ComponentModel.SPRING)
@SpringMapperConfig(generateConverterScan = true)
public interface MapperConfig {}
