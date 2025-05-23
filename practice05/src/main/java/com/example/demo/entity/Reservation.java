package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Reservation {
	@Id
	@Column(name="reservaion_idx")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idx;

	
}
