package org.datn.app.core.repo;

import org.datn.app.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByUsernameOrEmail(String username, String email);
}
