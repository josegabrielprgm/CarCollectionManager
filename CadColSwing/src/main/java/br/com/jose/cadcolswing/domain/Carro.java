package br.com.jose.cadcolswing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Carro {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int anoFab;
    private String apelido;
    private String placa;

    @ManyToOne
    @JoinColumn(name = "cpf")
    private Colecionador colecionador;

    public Carro() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnoFab() {
        return anoFab;
    }

    public void setAnoFab(int anoFab) {
        this.anoFab = anoFab;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Colecionador getColecionador() {
        return colecionador;
    }

    public void setColecionador(Colecionador colecionador) {
        this.colecionador = colecionador;
    }
}