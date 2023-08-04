package com.jsprestfulapi.application.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jsprestfulapi.application.controllers.PersonController;
import com.jsprestfulapi.application.mapper.DozerMapper;
import com.jsprestfulapi.domain.data.model.Person;
import com.jsprestfulapi.domain.data.vo.v1.PersonVO;
import com.jsprestfulapi.infrastructure.exceptions.RequiredObjectIsNullException;
import com.jsprestfulapi.infrastructure.exceptions.ResourceNotFoundException;
import com.jsprestfulapi.infrastructure.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	private PersonRepository _repository;
	
	@Autowired
	private PagedResourcesAssembler<PersonVO> _assembler;
	
	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {
		
		logger.info("Finding all people!");
		
		var personPage = _repository.findAll(pageable);
		
		var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));
		personVosPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return _assembler.toModel(personVosPage, link);
	}

	public PersonVO findById(Long id) {
		
		logger.info("Finding one PersonVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		var vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO create(PersonVO person) {
		
		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one PersonVO!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(_repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	@Transactional
	public PersonVO updateAge(Long id, int idade) {
		
		if(id == null) throw new RequiredObjectIsNullException();
		
		logger.info("Updating a PersonVO Age!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this Id!"));
		
		entity.setIdade(idade);
		
		var vo = DozerMapper.parseObject(_repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}
	
	public void delete(Long id) {
		
		logger.info("Deleting one PersonVO!");
		
		var entity = _repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		_repository.delete(entity);
		
	}
	
}
