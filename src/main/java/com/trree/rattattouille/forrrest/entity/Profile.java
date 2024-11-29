package com.trree.rattattouille.forrrest.entity;

import java.util.List;

import com.trree.rattattouille.global.entity.BaseTimeEntity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseTimeEntity {
    @Id
    private Long id;
    
    private String profileName;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profile_roles")
    private List<String> roles;
    
    @Builder
    public Profile(Long id, String profileName, List<String> roles) {
        this.id = id;
        this.profileName = profileName;
        this.roles = roles;
    }
}
