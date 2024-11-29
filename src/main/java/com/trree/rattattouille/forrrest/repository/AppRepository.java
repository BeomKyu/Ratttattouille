package com.trree.rattattouille.forrrest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trree.rattattouille.forrrest.entity.App;

public interface AppRepository extends JpaRepository<App, Long> {
    Optional<App> findByClientId(String clientId);
}
