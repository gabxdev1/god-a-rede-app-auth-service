package br.com.gabxdev.domain.ports.out;

import br.com.gabxdev.domain.model.RefreshToken;

public interface RefreshTokenOutputPort {
    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByTokenOrThrowNotFound(String token);
}
