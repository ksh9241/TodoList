package com.toy.todo.common;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface GenericMapper<D, E> {
	
	D entityFromDto (E entity);
	E dtoFromEntity (D dto);
	
	/**
	 * nullValuePropertyMappingStrategy : Source의 필드가 Null 일때 정책으로 Null인 값은 무시한다. 
	 * */
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void updateFromDto(D dto, @MappingTarget E entity);
	
 
	
	
	
}
