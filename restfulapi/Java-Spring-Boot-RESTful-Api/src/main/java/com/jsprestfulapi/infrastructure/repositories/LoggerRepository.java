package com.jsprestfulapi.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsprestfulapi.domain.data.model.Logger;

@Repository
public interface LoggerRepository extends JpaRepository<Logger, Long>{}
