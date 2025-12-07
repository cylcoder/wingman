package com.wingman.repository;

import com.wingman.entity.Gender;
import com.wingman.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  @Query(value = "SELECT * FROM users u "
      + "WHERE u.gender = :gender "
      + "ORDER BY RAND() LIMIT 3", nativeQuery = true)
  List<User> findRandomUsersByGender(
      @Param("gender") Gender gender
  );

}
