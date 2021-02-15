package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.domain.Evaluation;
import com.ms.dto.EvalResponseDto;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>{
	@Query("SELECT new com.ms.dto.EvalResponseDto(e.rating, e.review) "
			+ "FROM Evaluation e "
			+ "WHERE e.user.userIdx = :userIdx")
	List<EvalResponseDto> findAllByUserIdx(int userIdx);

	@Query("SELECT AVG(e.rating) "
			+ "FROM Evaluation e "
			+ "WHERE e.user.userIdx = :userIdx")
	double getAverage(int userIdx);
}
