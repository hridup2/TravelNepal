package com.travelNepal.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserSession {
	public enum Role {
		ADMIN,CUSTOMER;
	}
	
	@Id
	private Integer userId;
	private String sessionId;
	@Enumerated(EnumType.STRING)
	private Role role;
	private LocalDateTime loginTime;
}