package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms.domain.Report;
import com.ms.dto.ReportResponseDto;

public interface ReportRepository extends JpaRepository<Report, Integer>{
	@Query("SELECT new com.ms.dto.ReportResponseDto(e.content) "
			+ "FROM Report e "
			+ "WHERE e.user.userIdx = :userIdx")
	List<ReportResponseDto> findAllByUserIdx(int userIdx);
}
