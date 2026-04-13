package br.com.jose.cadcolswing.domain;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Colecionador {

	@Id
	private String cpf;

	@Override
	public int hashCode() {
		return Objects.hash(carros, cpf, idade, nome, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colecionador other = (Colecionador) obj;
		return Objects.equals(carros, other.carros) && Objects.equals(cpf, other.cpf) && idade == other.idade
				&& Objects.equals(nome, other.nome) && Objects.equals(telefone, other.telefone);
	}

	private String nome;
	private String telefone;
	private int idade;

	@OneToMany(mappedBy = "colecionador")
	private List<Carro> carros;

	public Colecionador() {
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public List<Carro> getCarros() {
		return carros;
	}

	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}
}