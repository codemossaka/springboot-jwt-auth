package ru.godsonpeya.springsecurityjwttest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.godsonpeya.springsecurityjwttest.entities.User;

import java.util.Optional;


public interface UserReopository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
