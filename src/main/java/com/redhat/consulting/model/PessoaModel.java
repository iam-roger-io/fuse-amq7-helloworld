package com.redhat.consulting.model;

import java.io.Serializable;

public class PessoaModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5638150087383684773L;

	private Long id;
	
	private String nome;
	private String cpf;
	private String telefone;
	
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return nome + ";" + cpf + ";" + telefone + ";" + email;
	}

	
	
}
