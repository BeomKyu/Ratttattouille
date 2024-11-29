package com.trree.rattattouille.forrrest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trree.rattattouille.forrrest.entity.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {  // ID 타입을 String으로 변경
    Optional<RefreshToken> findByRefreshToken(String token);
    boolean existsByRefreshToken(String token);
}