package com.jsprestfulapi.integrationtests.controller.withjson;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsprestfulapi.configs.TestConfigs;
import com.jsprestfulapi.integrationtests.testcontainers.AbstractIntegrationTest;
import com.jsprestfulapi.integrationtests.vo.PersonVO;
import com.jsprestfulapi.integrationtests.vo.wrappers.WrapperPersonVO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
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
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
		
		assertEquals("Guilherme", persistedPerson.getNome());
		assertEquals("Ruiz", persistedPerson.getSobrenome());
		assertEquals(19, persistedPerson.getIdade());
		assertEquals("Brasil", persistedPerson.getPais());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setIdade(20);
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
		
		assertEquals("Guilherme", persistedPerson.getNome());
		assertEquals("Ruiz", persistedPerson.getSobrenome());
		assertEquals(20, persistedPerson.getIdade());
		assertEquals("Brasil", persistedPerson.getPais());
	}

	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
		
		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Guilherme", persistedPerson.getNome());
		assertEquals("Ruiz", persistedPerson.getSobrenome());
		assertEquals(20, persistedPerson.getIdade());
		assertEquals("Brasil", persistedPerson.getPais());
	}
	
	@Test
	@Order(4)
	public void testDelete() throws JsonMappingException, JsonProcessingException {

		given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 0, "size", 10, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
		var people = wrapper.getEmbedded().getPersons();
		
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
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParams("page", 0, "size", 5, "direction", "asc")
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost/api/person/v1/5\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost/api/person/v1/3\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost/api/person/v1/2\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost/api/person/v1/1\"}}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost/api/person/v1/4\"}}}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost/api/person/v1?page=0&size=5&direction=asc\"}"));
	}
	
	private void mockPerson() {
		person.setNome("Guilherme");
		person.setSobrenome("Ruiz");
		person.setIdade(19);
		person.setPais("Brasil");
	}

}
