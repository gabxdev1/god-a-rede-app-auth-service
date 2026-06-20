package br.com.gabxdev.domain.ports.in;

import java.time.Instant;
import java.util.UUID;

public interface JwtInboundPort {
    String generateToken(String userId, Instant now);

    UUID validate(String token);
}
