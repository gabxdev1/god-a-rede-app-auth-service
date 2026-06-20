package br.com.gabxdev.domain.ports.in;

import br.com.gabxdev.domain.model.AccessToken;
import br.com.gabxdev.domain.model.UserCredential;

import java.util.UUID;

public interface UserCredentialInboundPort {

    UserCredential signUp(UserCredential userCredential);

    AccessToken authenticate(String email, String password);

    AccessToken refresh(String token);

    void logout(String token);
}
