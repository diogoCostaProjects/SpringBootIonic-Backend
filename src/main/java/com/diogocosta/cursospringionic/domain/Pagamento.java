package com.diogocosta.cursospringionic.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.diogocosta.cursospringionic.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy=InheritanceType.JOINED) 					// para mapear a classe genérica de pagamento, criando um tabelão que contém os campos da classe pai e das classes filhas (OBS: usar apenas quando há poucos campos nas classes filhas)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer estado; // Enum 
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id")
	@MapsId                                   // Garante que o campo é o mesmo do id do pedido pra não perder a referência 
	private Pedido pedido;
	
	
	
	protected Pagamento(){
		
	}
		

	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = (estado==null)? null:estado.getCod();
		this.pedido = pedido;
	}
	
	


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}


	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}


	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
