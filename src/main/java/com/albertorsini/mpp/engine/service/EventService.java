package com.albertorsini.mpp.engine.service;

import com.albertorsini.mpp.backend.elasticsearch.ElasticsearchClient;
import com.albertorsini.mpp.backend.kafka.Producer;
import com.albertorsini.mpp.engine.Utils;
import com.albertorsini.mpp.engine.exception.EventNotFoundException;
import com.albertorsini.mpp.model.Action;
import com.albertorsini.mpp.model.Event;
import com.albertorsini.mpp.model.EventRequest;
import com.albertorsini.mpp.repository.EventRepository;
import com.albertorsini.mpp.web.assembler.EventResourceAssembler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.albertorsini.mpp.model.Action.DELETE;
import static com.albertorsini.mpp.model.Action.INDEX;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

@Service
public class EventService {

  //todo mettere a configurazione
  private static final String TOPIC_NAME = "events";

  private final EventRepository eventRepository;
  private final ElasticsearchClient elasticsearchClient;
  private final EventResourceAssembler eventResourceAssembler;
  private final Producer<Event> producer;

  public EventService(
    final EventRepository eventRepository,
    final ElasticsearchClient elasticsearchClient,
    final EventResourceAssembler eventResourceAssembler,
    final Producer<Event> producer
  ) {
    this.eventRepository = eventRepository;
    this.elasticsearchClient = elasticsearchClient;
    this.eventResourceAssembler = eventResourceAssembler;
    this.producer = producer;
  }

  public Event create(final String walletId, final EventRequest eventRequest) {

    requireNonNull(eventRequest, "eventRequest must not be null");
    requireNonNull(walletId, "walletId must not be null");

    final Event entity = new Event();
    entity.setId(randomUUID().toString());
    entity.setAmount(eventRequest.getAmount());
    entity.setWalletId(walletId);
    entity.setDescription(eventRequest.getDescription());
    entity.setTag(Utils.convertToJson(eventRequest.getTag()));
    entity.setCurrency(eventRequest.getCurrency());
    entity.setAccountingDate(eventRequest.getAccountingDate());
    entity.setValueDate(eventRequest.getValueDate());
    entity.setType(eventRequest.getType());

    producer.sendMessageToTopic(entity, TOPIC_NAME);

    return entity;
  }

  public void saveAndIndex(final Event entity) {

    requireNonNull(entity, "event must not be null");

    final Event event = eventRepository.save(entity);

    final String body = convertEvent(event, INDEX);
    elasticsearchClient.index(body);
  }

  public Event findOne(final String walletId, final String eventId) {

    requireNonNull(walletId, "walletId must not be null");
    requireNonNull(eventId, "eventId must not be null");

    return eventRepository.findByIdAndWalletId(eventId, walletId)
      .orElseThrow(() -> new EventNotFoundException(eventId));
  }

  public Page<Event> findAll(final String walletId, final Pageable pageable) {

    requireNonNull(walletId, "walletId must not be null");

    return eventRepository.findByWalletId(walletId, pageable);
  }

  // TODO
  public Event update() {
    return null;
  }

  public Event delete(final String walletId, final String eventId) {

    requireNonNull(walletId, "walletId must not be null");
    requireNonNull(eventId, "eventId must not be null");

    final Event event = this.findOne(walletId, eventId);

    elasticsearchClient.index(convertEvent(event, DELETE));
    eventRepository.delete(event);

    return event;
  }

  @Transactional
  public void deleteAll(final String walletId) {

    requireNonNull(walletId, "walletId must not be null");

    eventRepository.deleteByWalletId(walletId);
  }

  private String convertEvent(final Event event, final Action action) {

    final ObjectNode actionNode = Utils.OBJECT_MAPPER.createObjectNode();

    final ObjectNode actionProperties = actionNode.putObject(action.toString().toLowerCase());
    actionProperties.put("_index", event.getWalletId());
    actionProperties.put("_id", event.getId());

    final JsonNode sourceNode = Utils.OBJECT_MAPPER.valueToTree(eventResourceAssembler.toResource(event));

    return action.equals(INDEX) ? actionNode + "\n" + sourceNode + "\n" : actionNode + "\n";
  }
}
