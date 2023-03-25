package main.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//replaced extends CrudRepository<User, Integer>

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findBySessionId(String sessionId);
}
