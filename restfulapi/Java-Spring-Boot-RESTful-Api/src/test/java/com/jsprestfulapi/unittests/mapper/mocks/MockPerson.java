package com.jsprestfulapi.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.jsprestfulapi.domain.data.model.Person;
import com.jsprestfulapi.domain.data.vo.v1.PersonVO;

public class MockPerson {

    public Person mockEntity() {
        return mockEntity(0);
    }
    
    public PersonVO mockVO() {
        return mockVO(0);
    }
    
    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<PersonVO> mockVOList() {
        List<PersonVO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }
    
    public Person mockEntity(Integer number) {
        Person person = new Person();
        person.setNome("Nome Test" + number);
        person.setSobrenome("Sobrenome Test" + number);
        person.setIdade(18);
        person.setId(number.longValue());
        person.setPais("Pais Test" + number);
        return person;
    }

    public PersonVO mockVO(Integer number) {
        PersonVO person = new PersonVO();
        person.setNome("Nome Test" + number);
        person.setSobrenome("Sobrenome Test" + number);
        person.setIdade(18);
        person.setKey(number.longValue());
        person.setPais("Pais Test" + number);
        return person;
    }

}
