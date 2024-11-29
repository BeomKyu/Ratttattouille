package com.trree.rattattouille.forrrest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trree.rattattouille.forrrest.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    // 기본 CRUD 메서드는 JpaRepository에서 제공됨
    Optional<Profile> findById(Long id);
    
    // 추가로 필요한 쿼리 메서드가 있다면 여기에 정의
    boolean existsById(Long id);
}