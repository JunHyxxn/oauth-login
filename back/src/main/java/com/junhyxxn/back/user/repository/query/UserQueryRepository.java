package com.junhyxxn.back.user.repository.query;

import com.junhyxxn.back.domain.entity.User;
import com.junhyxxn.back.domain.type.Provider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQueryRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndProvider(String email, Provider provider);

}
