package com.jsprestfulapi.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsprestfulapi.domain.data.model.Logger;
import com.jsprestfulapi.infrastructure.repositories.LoggerRepository;

@Service
public class LoggerService {
	
	@Autowired
	private final LoggerRepository _repository;

    public LoggerService(LoggerRepository repository) {
        this._repository = repository;
    }

    public void addLogMessage(String message) {
        Logger logger = new Logger();
        logger.setDescricao(message);
        _repository.save(logger);
    }
}
