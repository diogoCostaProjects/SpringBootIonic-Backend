package com.diogocosta.cursospringionic.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {
	private static final long serialVersionUID = 1L; 
	
	@EmbeddedId
	@JsonIgnore  // não será serializado 
	private ItemPedidoPK id = new ItemPedidoPK();  // Atributo para chave composta 
	
	private double desconto;
	private Integer quantidade;
	private double preco;

	public ItemPedido(){
		
	}

	public ItemPedido(Pedido pedido, Produto produto, double desconto, Integer quantidade, double preco) {
		super();
		id.setPedido(pedido);
		id.setProduto(produto);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.preco = preco;
	}

	public Double getSubtotal(){ // JSON já captura automaticamente métodos com 'get'
		return (preco-desconto) * quantidade;
	}
	
	
	public ItemPedidoPK getId() {
		return id;
	}
	
	@JsonIgnore
	public Pedido getPedido(){
		return id.getPedido();
	}
	
	public void setPedido(Pedido pedido){
		id.setPedido(pedido);
	}
		
	public Produto getProduto(){
		return id.getProduto();
	}	
	
	public void setProduto(Produto produto){
		id.setProduto(produto);
	}
	
	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		StringBuilder builder = new StringBuilder();
		builder.append(getProduto().getNome());
		builder.append(", Qte: ");
		builder.append(getQuantidade());
		builder.append(", Preço unitário: ");
		builder.append(nf.format(getPreco()));
		builder.append(", Subtotal: ");
		builder.append(nf.format(getSubtotal()));
		builder.append("\n");
		
		return builder.toString();
	}
	
	
	
	
	
}
