package com.jsprestfulapi.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jsprestfulapi.configs.TestConfigs;
import com.jsprestfulapi.integrationtests.testcontainers.AbstractIntegrationTest;
import com.jsprestfulapi.integrationtests.vo.PersonVO;
import com.jsprestfulapi.integrationtests.vo.pagedmodels.PagedModelPerson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerXmlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static XmlMapper objectMapper;

	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new XmlMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonVO();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		specification = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.body(person)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getIdade());
		assertNotNull(persistedPerson.getPais());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Paulo", persistedPerson.getNome());
		assertEquals("Souza", persistedPerson.getSobrenome());
		assertEquals(22, persistedPerson.getIdade());
		assertEquals("Brasil", persistedPerson.getPais());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setIdade(23);
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.body(person)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getIdade());
		assertNotNull(persistedPerson.getPais());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Paulo", persistedPerson.getNome());
		assertEquals("Souza", persistedPerson.getSobrenome());
		assertEquals(23, persistedPerson.getIdade());
		assertEquals("Brasil", persistedPerson.getPais());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_ANGULAR)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getNome());
		assertNotNull(persistedPerson.getSobrenome());
		assertNotNull(persistedPerson.getIdade());
		assertNotNull(persistedPerson.getPais());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Paulo", persistedPerson.getNome());
		assertEquals("Souza", persistedPerson.getSobrenome());
		assertEquals(23, persistedPerson.getIdade());
		assertEquals("Brasil", persistedPerson.getPais());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.pathParam("id", person.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "size", 10, "direction", "asc")
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PagedModelPerson wrapper = objectMapper.readValue(content, PagedModelPerson.class);
		var people = wrapper.getContent();
		
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getNome());
		assertNotNull(foundPersonOne.getSobrenome());
		assertNotNull(foundPersonOne.getIdade());
		assertNotNull(foundPersonOne.getPais());
		
		assertEquals(5, foundPersonOne.getId());
		
		assertEquals("Albert", foundPersonOne.getNome());
		assertEquals("Einstein", foundPersonOne.getSobrenome());
		assertEquals(70, foundPersonOne.getIdade());
		assertEquals("Alemanha", foundPersonOne.getPais());
		
		PersonVO foundPersonTwo = people.get(1);
		
		assertNotNull(foundPersonTwo.getId());
		assertNotNull(foundPersonTwo.getNome());
		assertNotNull(foundPersonTwo.getSobrenome());
		assertNotNull(foundPersonTwo.getIdade());
		assertNotNull(foundPersonTwo.getPais());
		
		assertEquals(3, foundPersonTwo.getId());
		
		assertEquals("Guilherme", foundPersonTwo.getNome());
		assertEquals("Ruiz da Silva", foundPersonTwo.getSobrenome());
		assertEquals(19, foundPersonTwo.getIdade());
		assertEquals("Brasil", foundPersonTwo.getPais());
		
	}
	
	@Test
	@Order(6)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.queryParams("page", 0, "size", 5, "direction", "asc")
				.accept(TestConfigs.CONTENT_TYPE_XML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/5</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/3</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/2</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/1</href></links>"));
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1/4</href></links>"));
		
		assertTrue(content.contains("<links><rel>self</rel><href>http://localhost:8888/api/person/v1?page=0&amp;size=5&amp;direction=asc</href></links>"));
		assertTrue(content.contains("<page><size>5</size><totalElements>5</totalElements><totalPages>1</totalPages><number>0</number></page>"));
	}
	
	private void mockPerson() {
		person.setNome("Paulo");
		person.setSobrenome("Souza");
		person.setIdade(22);
		person.setPais("Brasil");
	}

}
