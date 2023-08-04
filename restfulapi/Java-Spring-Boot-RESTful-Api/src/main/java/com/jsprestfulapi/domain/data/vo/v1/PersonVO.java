package com.jsprestfulapi.domain.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({"id","nome","sobrenome","idade","pais"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Mapping("id")
	@JsonProperty("id")
	private Long key;
	private String nome;
	private String sobrenome;
	private int idade;
	private String pais;
	
	public PersonVO() {}

	public Long getKey() {
		return key;
	}

	public String getNome() {
		return nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public int getIdade() {
		return idade;
	}

	public String getPais() {
		return pais;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(idade, key, nome, pais, sobrenome);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		return idade == other.idade && Objects.equals(key, other.key) && Objects.equals(nome, other.nome)
				&& Objects.equals(pais, other.pais) && Objects.equals(sobrenome, other.sobrenome);
	}
	
}
