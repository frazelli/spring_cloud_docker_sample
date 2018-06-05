package com.bankofaffiliates.userservice.repository;

import com.bankofaffiliates.common.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailRecoveryToken(String recoveryToken);
}
