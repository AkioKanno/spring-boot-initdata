package test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
