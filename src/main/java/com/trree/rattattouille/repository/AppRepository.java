package com.trree.rattattouille.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trree.rattattouille.entity.App;

public interface AppRepository extends JpaRepository<App, Long> {
    Optional<App> findByClientId(String clientId);
}
