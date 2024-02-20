package com.epam.learn.rest.jmpcloudserviceimpl;

import com.epam.learn.rest.jmpdto.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User, Long> {
}
