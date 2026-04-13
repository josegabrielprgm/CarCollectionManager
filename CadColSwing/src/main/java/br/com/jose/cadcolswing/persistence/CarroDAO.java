package br.com.jose.cadcolswing.persistence;

import java.util.List;

import br.com.jose.cadcolswing.domain.Carro;

public class CarroDAO extends GenericDAO<Carro> {

	public CarroDAO() {
		super(Carro.class);
	}

	public void apagarPeloCpf(String cpf) {
		String sql = " o.colecionador.cpf = '" + cpf + "'";

		List<Carro> carros = listar(sql);

		for (Carro carro : carros) {
			apagar(carro.getId());
		}
	}

	public Carro buscarPelaPlaca(String placa) {
		List<Carro> lista = em.createQuery("SELECT c FROM Carro c WHERE c.placa = :placa", Carro.class)
				.setParameter("placa", placa).getResultList();

		return lista.isEmpty() ? null : lista.get(0);
	}
}