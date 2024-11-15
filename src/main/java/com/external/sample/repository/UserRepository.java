package com.external.sample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.external.sample.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 기본 CRUD 메서드는 JpaRepository에서 제공됨
    Optional<User> findById(Long id);
    
    // 추가로 필요한 쿼리 메서드가 있다면 여기에 정의
    boolean existsById(Long id);
}