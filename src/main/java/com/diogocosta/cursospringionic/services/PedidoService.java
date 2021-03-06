package com.diogocosta.cursospringionic.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.diogocosta.cursospringionic.domain.Cliente;
import com.diogocosta.cursospringionic.domain.ItemPedido;
import com.diogocosta.cursospringionic.domain.PagamentoComBoleto;
import com.diogocosta.cursospringionic.domain.Pedido;
import com.diogocosta.cursospringionic.domain.enums.EstadoPagamento;
import com.diogocosta.cursospringionic.domain.security.UserSS;
import com.diogocosta.cursospringionic.repositories.ItemPedidoRepository;
import com.diogocosta.cursospringionic.repositories.PagamentoRepository;
import com.diogocosta.cursospringionic.repositories.PedidoRepository;
import com.diogocosta.cursospringionic.services.exceptions.AuthorizationException;
import com.diogocosta.cursospringionic.services.exceptions.ObjectNotFoundException;

//classe de serviço para separação por camadas

@Service
public class PedidoService {

	
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired 
	private EmailService emailService;

		
	
	
	public Pedido find(Integer id) {

		Pedido obj = repo.findOne(id);

		if (obj == null) { // testa se não retornou e lança a exeção caso nao
							// venha nada
			throw new ObjectNotFoundException("Objeto não encontrado Id: " + id + ",Tipo: " + Pedido.class.getName());
		}
		return obj;
	}

	
	
	
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));  // Desta maneira insere os dados do cliente, pegando pelo id que já era inseido no POST
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}	
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
		    ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		System.out.println(obj);
		return obj;

	}
	
	//retorna os pedidos do cliente acessado, verificando os dados de autenticação
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		UserSS user = UserService.authenticated();	
		
		if(user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		PageRequest pageRequest = new PageRequest(page,linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		
		return repo.findByCliente(cliente, pageRequest);
	}
}
