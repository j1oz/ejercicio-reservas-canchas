package com.jloz.reservascanchas.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializadorBD implements CommandLineRunner {
    private final SchemaCreate schemaCreate;

    public InicializadorBD(SchemaCreate schemaCreate) {
        this.schemaCreate = schemaCreate;
    }

    @Override
    public void run(String... args) {
        schemaCreate.crearTablas();
    }
}