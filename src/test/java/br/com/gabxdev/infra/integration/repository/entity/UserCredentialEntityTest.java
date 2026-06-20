package br.com.gabxdev.infra.integration.repository.entity;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserCredentialEntityTest {

    @Test
    void shouldBeNewByDefaultAndBecomePersistedAfterMarkPersisted() {
        var entity = new UserCredentialEntity();
        entity.setId(UUID.randomUUID());

        assertTrue(entity.isNew());

        entity.markPersisted();

        assertFalse(entity.isNew());
    }
}

