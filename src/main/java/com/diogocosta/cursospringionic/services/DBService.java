package com.diogocosta.cursospringionic.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Categoria;
import com.diogocosta.cursospringionic.domain.Cidade;
import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.domain.Endereco;
import com.diogocosta.cursospringionic.domain.Estado;
import com.diogocosta.cursospringionic.domain.ItemPedido;
import com.diogocosta.cursospringionic.domain.Pagamento;
import com.diogocosta.cursospringionic.domain.PagamentoComBoleto;
import com.diogocosta.cursospringionic.domain.PagamentoComCartao;
import com.diogocosta.cursospringionic.domain.Pedido;
import com.diogocosta.cursospringionic.domain.Produto;
import com.diogocosta.cursospringionic.domain.enums.EstadoPagamento;
import com.diogocosta.cursospringionic.domain.enums.TipoCliente;
import com.diogocosta.cursospringionic.repositories.CategoriaRepository;
import com.diogocosta.cursospringionic.repositories.CidadeRepository;
import com.diogocosta.cursospringionic.repositories.ClienteRepository;
import com.diogocosta.cursospringionic.repositories.EnderecoRepository;
import com.diogocosta.cursospringionic.repositories.EstadoRepository;
import com.diogocosta.cursospringionic.repositories.ItemPedidoRepository;
import com.diogocosta.cursospringionic.repositories.PagamentoRepository;
import com.diogocosta.cursospringionic.repositories.PedidoRepository;
import com.diogocosta.cursospringionic.repositories.ProdutoRepository;

@Service
public class DBService { // Faz toda instanciação do banco de dados 
	
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	


	public void instantiateDatabase() throws ParseException { // o throws é porque o SimpleDateFormat pode gerar a exceção de parse 
		
		Categoria cat1 = new Categoria(1, "Informatica");
		Categoria cat2 = new Categoria(2, "Escritorio");
		Categoria cat3 = new Categoria(3, "Cama mesa e banho");
		Categoria cat4 = new Categoria(4, "Jardinagem");
		Categoria cat5 = new Categoria(5, "Eletronicos");
		Categoria cat6 = new Categoria(6, "Perfumaria");
		Categoria cat7 = new Categoria(7, "Decoração");

		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		categoriaRepository.flush();

		Produto p1 = new Produto(1, "computador", 2000.00);
		Produto p2 = new Produto(2, "impressora", 800.00);
		Produto p3 = new Produto(3, "mouse", 80.00);
		Produto p4 = new Produto(4, "mesa de escritório", 300.00);
		Produto p5 = new Produto(5, "toalha", 50.00);
		Produto p6 = new Produto(6, "colcha", 200.00);
		Produto p7 = new Produto(7, "TV true color", 1200.00);
		Produto p8 = new Produto(8, "roçadeira", 800.00);
		Produto p9 = new Produto(9, "abajour", 100.00);
		Produto p10 = new Produto(10, "pindurico", 80.00);
		Produto p11 = new Produto(11, "shampoo", 80.00);

		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		produtoRepository.flush();

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().add(p11);

		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		categoriaRepository.flush(); // Salvo denovo para linkar os objetos

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		produtoRepository.flush();

		Estado est1 = new Estado(1, "Minas Gerais");
		Estado est2 = new Estado(2, "Sao Paulo");

		estadoRepository.save(Arrays.asList(est1, est2));
		estadoRepository.flush();

		Cidade c1 = new Cidade(1, "Uberlandia", est1);
		Cidade c2 = new Cidade(2, "Sao Paulo", est2);
		Cidade c3 = new Cidade(3, "Campinas", est2);

		cidadeRepository.save(Arrays.asList(c1, c2, c3));
		cidadeRepository.flush();

		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(1, "Maria Silva", "diogocosta.jar@gmail.com", "3619210929", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12121290", "98102981"));

		clienteRepository.save(cli1);
		clienteRepository.flush();

		Endereco e1 = new Endereco(1, "Rua Flores", "300", "apto 303", "Jardim", "3822200", c1, cli1);
		Endereco e2 = new Endereco(2, "Avenida Matos", "105", "sala 800", "Centro", "3899000", c2, cli1);

		enderecoRepository.save(Arrays.asList(e1, e2));
		enderecoRepository.flush();

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.save(cli1);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Pedido ped1 = new Pedido(1, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(2, sdf.parse("10/10/2017 10:32"), cli1, e2);

		Pagamento pgto1 = new PagamentoComCartao(1, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);

		Pagamento pgto2 = new PagamentoComBoleto(2, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgto2);

		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pedidoRepository.flush();
		pagamentoRepository.save(Arrays.asList(pgto1, pgto2));
		pagamentoRepository.flush();

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2)); // apenas faz a
																// referência
		clienteRepository.save(cli1);

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 3, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped1.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));

	}

}
