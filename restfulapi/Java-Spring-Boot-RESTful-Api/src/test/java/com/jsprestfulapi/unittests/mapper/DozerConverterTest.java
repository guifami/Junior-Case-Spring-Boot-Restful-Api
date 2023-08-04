package com.jsprestfulapi.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jsprestfulapi.application.mapper.DozerMapper;
import com.jsprestfulapi.domain.data.model.Person;
import com.jsprestfulapi.domain.data.vo.v1.PersonVO;
import com.jsprestfulapi.unittests.mapper.mocks.MockPerson;

public class DozerConverterTest {
    
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PersonVO output = DozerMapper.parseObject(inputObject.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Nome Test0", output.getNome());
		assertEquals("Sobrenome Test0", output.getSobrenome());
		assertEquals(18, output.getIdade());
		assertEquals("Pais Test0", output.getPais());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), PersonVO.class);
        PersonVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Nome Test0", outputZero.getNome());
		assertEquals("Sobrenome Test0", outputZero.getSobrenome());
		assertEquals(18, outputZero.getIdade());
		assertEquals("Pais Test0", outputZero.getPais());
        
        PersonVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("Nome Test7", outputSeven.getNome());
		assertEquals("Sobrenome Test7", outputSeven.getSobrenome());
		assertEquals(18, outputSeven.getIdade());
		assertEquals("Pais Test7", outputSeven.getPais());
        
        PersonVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("Nome Test12", outputTwelve.getNome());
		assertEquals("Sobrenome Test12", outputTwelve.getSobrenome());
		assertEquals(18, outputTwelve.getIdade());
		assertEquals("Pais Test12", outputTwelve.getPais());
    }

    @Test
    public void parseVOToEntityTest() {
        Person output = DozerMapper.parseObject(inputObject.mockVO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Nome Test0", output.getNome());
		assertEquals("Sobrenome Test0", output.getSobrenome());
		assertEquals(18, output.getIdade());
		assertEquals("Pais Test0", output.getPais());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Person> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Person.class);
        Person outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Nome Test0", outputZero.getNome());
		assertEquals("Sobrenome Test0", outputZero.getSobrenome());
		assertEquals(18, outputZero.getIdade());
		assertEquals("Pais Test0", outputZero.getPais());
        
        Person outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Nome Test7", outputSeven.getNome());
		assertEquals("Sobrenome Test7", outputSeven.getSobrenome());
		assertEquals(18, outputSeven.getIdade());
		assertEquals("Pais Test7", outputSeven.getPais());
        
        Person outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Nome Test12", outputTwelve.getNome());
		assertEquals("Sobrenome Test12", outputTwelve.getSobrenome());
		assertEquals(18, outputTwelve.getIdade());
		assertEquals("Pais Test12", outputTwelve.getPais());
    }
}
