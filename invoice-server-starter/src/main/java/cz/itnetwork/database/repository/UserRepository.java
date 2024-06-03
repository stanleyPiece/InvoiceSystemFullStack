package cz.itnetwork.database.repository;

import cz.itnetwork.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Method for finding a user by their email
     *
     * @param username user's name
     * @return UserEntity found user
     */
    Optional<UserEntity> findByEmail(String username);
}
