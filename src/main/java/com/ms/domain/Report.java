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
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int reportIdx;

	@ManyToOne
	@JoinColumn(name = "userIdx")
	User user;

	String content;

	@Builder
	public Report(User user, String content) {
		this.user = user;
		this.content = content;
	}

}
