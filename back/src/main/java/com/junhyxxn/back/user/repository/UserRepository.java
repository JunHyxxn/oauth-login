package com.junhyxxn.back.user.repository;

import com.junhyxxn.back.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
