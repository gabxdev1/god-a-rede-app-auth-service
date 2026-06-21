package br.com.gabxdev.domain.ports.in;

import br.com.gabxdev.domain.model.AccessToken;
import br.com.gabxdev.domain.model.User;

public interface UserInboundPort {

    User signUp(User user);

    AccessToken authenticate(String email, String password);

    AccessToken refresh(String token);

    void logout(String token);
}
