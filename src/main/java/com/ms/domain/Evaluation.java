package com.ms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evaluation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int evaluationIdx;

	int rating;
	String review;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	@Builder
	public Evaluation(int rating, String review, User user) {
		this.rating = rating;
		this.review = review;
		this.user = user;
	}
}
