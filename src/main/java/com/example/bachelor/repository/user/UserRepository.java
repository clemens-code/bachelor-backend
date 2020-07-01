package com.example.bachelor.repository.user;

import com.example.bachelor.entities.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
