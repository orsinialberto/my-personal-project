package com.albertorsini.mpp.web.assembler;

import com.albertorsini.mpp.engine.Utils;
import com.albertorsini.mpp.model.Event;
import com.albertorsini.mpp.model.EventResource;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

@Service
public class EventResourceAssembler implements ResourceAssembler<Event, EventResource> {

  @Override
  public EventResource toResource(final Event event) {

    final EventResource eventResource = new EventResource();
    eventResource.setId(event.getId());
    eventResource.setAmount(event.getAmount());
    eventResource.setWalletId(event.getWalletId());
    eventResource.setDescription(event.getDescription());
    eventResource.setTag(Utils.convertToObject(event.getTag(), new TypeReference<>() {}));
    eventResource.setEventType(event.getType());
    eventResource.setCurrency(event.getCurrency());
    eventResource.setAccountingDate(event.getAccountingDate());
    eventResource.setValueDate(event.getValueDate());
    eventResource.setRegisteredAt(event.getRegisteredAt());
    eventResource.setUpdatedAt(event.getUpdatedAt());

    return eventResource;
  }

}
