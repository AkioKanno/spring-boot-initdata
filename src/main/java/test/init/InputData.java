package test.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import test.entity.UserEntity;
import test.repository.UserRepository;

public class InputData {

    @Autowired
    UserRepository repository;

    @PostConstruct
    public void insertUser() {
        UserEntity entity = new UserEntity();
        entity.name = "testUser";
        this.repository.save(entity);
    }
}
