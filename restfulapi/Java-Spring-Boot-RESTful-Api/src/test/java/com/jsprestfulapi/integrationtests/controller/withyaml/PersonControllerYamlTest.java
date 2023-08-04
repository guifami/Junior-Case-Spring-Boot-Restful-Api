package com.jsprestfulapi.integrationtests.controller.withyaml;

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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jsprestfulapi.configs.TestConfigs;
import com.jsprestfulapi.integrationtests.controller.withyaml.mapper.YMLMapper;
import com.jsprestfulapi.integrationtests.testcontainers.AbstractIntegrationTest;
import com.jsprestfulapi.integrationtests.vo.PersonVO;
import com.jsprestfulapi.integrationtests.vo.pagedmodels.PagedModelPerson;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static YMLMapper objectMapper;

	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		
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
		
		var persistedPerson = given().spec(specification)
				.config(RestAssuredConfig.config()
						.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
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
		
		var persistedPerson = given().spec(specification)
				.config(RestAssuredConfig.config()
						.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
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
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
			
		var persistedPerson = given().spec(specification)
				.config(RestAssuredConfig.config()
						.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_ANGULAR)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
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
		.config(RestAssuredConfig.config()
				.encoderConfig(EncoderConfig.encoderConfig()
						.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.pathParam("id", person.getId())
					.when()
					.delete("{id}")
				.then()
					.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specification)
				.config(RestAssuredConfig.config()
						.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YML, ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 0, "size", 10, "direction", "asc")
				.accept(TestConfigs.CONTENT_TYPE_YML)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PagedModelPerson.class, objectMapper);
		
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
		
		var unthreatedContent = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YML)
				.accept(TestConfigs.CONTENT_TYPE_YML)
				.queryParams("page", 0, "size", 5, "direction", "asc")
				.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
						.asString();
		
		var content = unthreatedContent.replace("\n", "").replace("\r", "");
		
		assertTrue(content.contains("rel: \"self\"    href: \"http://localhost/api/person/v1/5\""));
		assertTrue(content.contains("rel: \"self\"    href: \"http://localhost/api/person/v1/3\""));
		assertTrue(content.contains("rel: \"self\"    href: \"http://localhost/api/person/v1/2\""));
		assertTrue(content.contains("rel: \"self\"    href: \"http://localhost/api/person/v1/1\""));
		assertTrue(content.contains("rel: \"self\"    href: \"http://localhost/api/person/v1/4\""));

		assertTrue(content.contains("rel: \"self\"  href: \"http://localhost/api/person/v1?page=0&size=5&direction=asc\""));
		assertTrue(content.contains("page:  size: 5  totalElements: 5  totalPages: 1  number: 0"));
	}
	
	private void mockPerson() {
		person.setNome("Guilherme");
		person.setSobrenome("Ruiz");
		person.setIdade(19);
		person.setPais("Brasil");
	}

}
