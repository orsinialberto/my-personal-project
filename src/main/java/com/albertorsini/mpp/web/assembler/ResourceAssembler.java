package com.albertorsini.mpp.web.assembler;

import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;

public interface ResourceAssembler<E, R> {

  R toResource(E var1);

  default List<R> toResource(List<E> entity) {

    return entity
      .stream()
      .map(this::toResource)
      .collect(toList());
  }

  default Page<R> toResource(final Page<E> event) {

    return event.map(this::toResource);
  }
}
