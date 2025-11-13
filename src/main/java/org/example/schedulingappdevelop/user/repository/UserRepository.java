package org.example.schedulingappdevelop.user.repository;

import org.example.schedulingappdevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
