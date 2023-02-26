package org.codeai.ecommerce.repository;

import org.codeai.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


  Optional<Object> findByUsernameAndPassword(String username, String password);

  Optional<User> findByEmail(String email);

Optional<User> findByUsername(String username);
}
