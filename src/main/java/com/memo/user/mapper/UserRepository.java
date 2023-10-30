package com.memo.user.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.memo.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	// null or 채워져 있거나 (UserEntity 단건)
	
	public UserEntity findByLoginId(String loginId);
	
}
