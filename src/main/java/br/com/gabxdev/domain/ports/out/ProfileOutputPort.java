package br.com.gabxdev.domain.ports.out;


import br.com.gabxdev.domain.model.Profile;

public interface ProfileOutputPort {
    void criarProfile(Profile profile);
}
