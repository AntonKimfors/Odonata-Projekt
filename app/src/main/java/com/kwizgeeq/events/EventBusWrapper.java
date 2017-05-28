package com.kwizgeeq.events;

import com.google.common.eventbus.EventBus;

/**
 * Created by Henrik on 14/05/2017.
 *
 * @author Henrik Håkansson
 * revised by Are Ehnberg, Marcus Olsson Lindvärn and Anton Kimfors
 */

public class EventBusWrapper {

    // Google Guava Eventbus
    public static final com.google.common.eventbus.EventBus BUS = new com.google.common.eventbus.EventBus();
    // Outgoing from model to GUI
}
