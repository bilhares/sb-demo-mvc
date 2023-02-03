package com.curso.boot.domain;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CARGOS")
public class Cargo extends AbstractEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo nome obrigatório.")
	@Size(max = 60, message = "O nome deve conter no máximo {max} caracteres.")
	@Column(name = "nome", nullable = false, unique = true, length = 60)
	private String nome;

	@NotNull(message = "Selecione um departamento.")
	@ManyToOne
	@JoinColumn(name = "id_departamento_fk")
	private Departamento departamento;

	@OneToMany(mappedBy = "cargo")
	private List<Funcionario> funcionarios;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

}
