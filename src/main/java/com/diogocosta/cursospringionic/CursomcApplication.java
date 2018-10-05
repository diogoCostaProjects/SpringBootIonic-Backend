package com.diogocosta.cursospringionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

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
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		
		Categoria cat1 = new Categoria(1, "Informatica");
		Categoria cat2 = new Categoria(2, "Escritorio");
		Categoria cat3 = new Categoria(4, "Cama mesa e banho");
		Categoria cat4 = new Categoria(5, "Jardinagem");
		Categoria cat5 = new Categoria(6, "Eletronicos");
		Categoria cat6 = new Categoria(7, "Perfumaria");
		Categoria cat7 = new Categoria(8, "Decoração");
		
		
		
		Produto p1 = new Produto(1, "computador", 2000.00);
		Produto p2 = new Produto(2, "impressora", 800.00);
		Produto p3 = new Produto(3, "mouse", 80.00);
		
		categoriaRepository.save(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
	    
		categoriaRepository.flush(); // utilizado pois os objetos estavam como Disatached
	    produtoRepository.flush();
		
	    cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().add(p2);
		
		p1.getCategorias().add(cat1);
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1,cat2));
		produtoRepository.save(Arrays.asList(p1,p2,p3));
	
	 
		Estado est1 = new Estado(1,"Minas Gerais");
		Estado est2 = new Estado(2,"Sao Paulo");
		
		estadoRepository.save(Arrays.asList(est1,est2));
		estadoRepository.flush();
		
		Cidade c1 = new Cidade(1,"Uberlandia",est1);
		Cidade c2 = new Cidade(2,"Sao Paulo",est2);
		Cidade c3 = new Cidade(3,"Campinas",est2);
				
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		cidadeRepository.flush();
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est1.getCidades().addAll(Arrays.asList(c2,c3));	
		
		estadoRepository.save(Arrays.asList(est1,est2));
		cidadeRepository.save(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(1,"Maria Silva", "maria@gmail.com","3619210929",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12121290","98102981"));
			
		clienteRepository.save(cli1);
	    clienteRepository.flush();
			
		    	    
	    Endereco e1 = new Endereco(1,"Rua Flores", "300", "apto 303", "Jardim", "3822200", c1, cli1);
		Endereco e2 = new Endereco(2,"Avenida Matos", "105", "sala 800", "Centro", "3899000", c2, cli1);
			
		enderecoRepository.save(Arrays.asList(e1,e2));
		enderecoRepository.flush();
		
	    cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
	    
	    clienteRepository.save(cli1);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	    	    
	    Pedido ped1 = new Pedido(1,sdf.parse("30/09/2017 10:32"),cli1,e1);	
	    Pedido ped2 = new Pedido(2,sdf.parse("10/10/2017 10:32"),cli1,e2);	
	    
	    
	    Pagamento pgto1 = new PagamentoComCartao(1,EstadoPagamento.QUITADO,ped1,6);
	    ped1.setPagamento(pgto1);
	   
	    Pagamento pgto2 = new PagamentoComBoleto(2,EstadoPagamento.PENDENTE,ped2,sdf.parse("20/10/2017 00:00"),null);
	    ped2.setPagamento(pgto2);
	   
	    pedidoRepository.save(Arrays.asList(ped1,ped2));
	    pedidoRepository.flush();
	    pagamentoRepository.save(Arrays.asList(pgto1,pgto2));
	    pagamentoRepository.flush();
	   	    
	    cli1.getPedidos().addAll(Arrays.asList(ped1,ped2)); 			// apenas faz a referência
	    clienteRepository.save(cli1);
	    
	    ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
	    ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 3, 80.00);
	    ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
	    
	    ped1.getItens().addAll(Arrays.asList(ip1,ip2));
	    ped1.getItens().addAll(Arrays.asList(ip3));
	    
	    p1.getItens().addAll(Arrays.asList(ip1));
	    p2.getItens().addAll(Arrays.asList(ip3));
	    p3.getItens().addAll(Arrays.asList(ip2));
	    
	    
	    itemPedidoRepository.save(Arrays.asList(ip1,ip2,ip3));
	    
	    
	   
	    
	   
	    
	    
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
		
		
	
	
	 // Esse cara (ComandLineRunner) roda uma Thread com o "run" e instancia os objetos por fora, 
	//  já preparando o banco H2, que funciona todo m memória,
	//  e por isso não guarda nada em disco como um banco normal
}
