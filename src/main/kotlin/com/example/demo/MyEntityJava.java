package com.example.demo;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Wither;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Value
@Wither
@Entity
@RequiredArgsConstructor
public class MyEntityJava {

    public MyEntityJava() {
        id = null;
        version = 0;
        name = "";
        description = "";
    }

    @Id
    @GeneratedValue
    private final UUID id;
    @Version
    private final long version;
    private final String name;
    private final String description;
}
