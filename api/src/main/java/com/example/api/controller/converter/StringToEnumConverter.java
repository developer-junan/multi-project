package com.example.api.controller.converter;

import com.example.api.util.SortTypeEnum;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, SortTypeEnum> {

    @Override
    public SortTypeEnum convert(String source) {
        return SortTypeEnum.valueOf(source.toUpperCase());
    }
}