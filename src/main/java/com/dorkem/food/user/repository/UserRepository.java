package com.dorkem.food.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dorkem.food.user.entity.OAuthProvider;
import com.dorkem.food.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByPhoneNumber(String phoneNumber);
	Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
