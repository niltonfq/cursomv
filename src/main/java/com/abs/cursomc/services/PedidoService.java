package com.abs.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.cursomc.domain.ItemPedido;
import com.abs.cursomc.domain.PagamentoComBoleto;
import com.abs.cursomc.domain.Pedido;
import com.abs.cursomc.domain.enums.EstadoPagamento;
import com.abs.cursomc.repositories.ItemPedidoRepository;
import com.abs.cursomc.repositories.PagamentoRepository;
import com.abs.cursomc.repositories.PedidoRepository;
import com.abs.cursomc.repositories.ProdutoRepository;
import com.abs.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo; 
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setEmissao(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.Pendente);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			
			boletoService.preencherPagamentoComBoleto(pagto, obj.getEmissao());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
			
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
