package com.albertorsini.mpp.repository;

import com.albertorsini.mpp.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EventRepository extends PagingAndSortingRepository<Event, String> {

  Optional<Event> findByIdAndWalletId(final String id, final String walletId);

  Page<Event> findByWalletId(final String walletId, final Pageable pageable);

  void deleteByWalletId(final String walletId);
}
