package org.example.user.user.repository;

import org.example.user.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
}
