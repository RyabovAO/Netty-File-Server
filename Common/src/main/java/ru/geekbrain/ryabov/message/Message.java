package ru.geekbrain.ryabov.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,
            property = "type"
)

public abstract class Message {
}
