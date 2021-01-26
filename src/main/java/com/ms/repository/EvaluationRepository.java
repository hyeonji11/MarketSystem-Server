package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.domain.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>{

}
