package io.goribco.apis.helper.emails;

public enum EmailEvent {
    SEND(1);
    private final long id;

    EmailEvent(long _id) {
        id = _id;
    }
}