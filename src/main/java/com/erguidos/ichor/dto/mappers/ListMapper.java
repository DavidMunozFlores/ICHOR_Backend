package com.erguidos.ichor.dto.mappers;

import java.util.List;
import java.util.function.Function;

import com.erguidos.ichor.dto.response.ListWrapper;

public class ListMapper {
    private ListMapper() {
        throw new UnsupportedOperationException("Don't instantiate ListMapper");
    }
    
    public static <T> ListWrapper<T> toListWrapper(List<T> toWrap) {
        return new ListWrapper<T>(toWrap);
    }
    
    public static <T, R> ListWrapper<R> toListWrapper(List<T> toWrap, Function<T, R> mapEachFirst) {
        return new ListWrapper<R>(
            toWrap.stream()
                  .map(mapEachFirst)
                  .toList()
        );
    }
}
