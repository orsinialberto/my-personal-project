package com.albertorsini.mpp.web.controller;

import com.albertorsini.mpp.engine.service.EventService;
import com.albertorsini.mpp.engine.service.WalletService;
import com.albertorsini.mpp.model.Event;
import com.albertorsini.mpp.model.EventRequest;
import com.albertorsini.mpp.model.EventResource;
import com.albertorsini.mpp.web.assembler.EventResourceAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/{walletId}/event")
public class EventController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);

  private final EventService eventService;
  private final WalletService walletService;
  private final EventResourceAssembler eventResourceAssembler;

  public EventController(
    final EventService eventService,
    final WalletService walletService,
    final EventResourceAssembler eventResourceAssembler) {
    this.eventService = eventService;
    this.walletService = walletService;
    this.eventResourceAssembler = eventResourceAssembler;
  }

  @PostMapping
  public EventResource create(final @PathVariable String walletId, final @RequestBody EventRequest eventRequest) {

    LOGGER.info("creating event");

    final Event event = eventService.create(walletId, eventRequest);

    LOGGER.info("created event {}", event.getId());

    return eventResourceAssembler.toResource(event);
  }

  @GetMapping("/{eventId}")
  public EventResource get(final @PathVariable String walletId, final @PathVariable String eventId) {

    LOGGER.info("searching event {}", eventId);

    final Event event = eventService.findOne(walletId, eventId);

    LOGGER.info("found event {}", event.getId());

    return eventResourceAssembler.toResource(event);
  }

  @GetMapping
  public Page<EventResource> getAll(final @PathVariable String walletId, final @PageableDefault Pageable pageable) {

    LOGGER.info("searching all events for wallet {}", walletId);

    final Page<Event> events = eventService.findAll(walletId, pageable);

    return eventResourceAssembler.toResource(events);
  }

  @DeleteMapping("/{eventId}")
  public String delete(final @PathVariable String walletId, final @PathVariable String eventId) {

    LOGGER.info("deleting event {}", eventId);

    final Event event = eventService.delete(walletId, eventId);
    final Double amount = event.getAmount() * -1;

    walletService.updateTotals(
      walletId,
      amount,
      event.getType()
    );

    LOGGER.info("deleted event {}", eventId);

    return eventId;
  }

}
