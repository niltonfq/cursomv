package com.abs.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.abs.cursomc.domain.Categoria;
import com.abs.cursomc.domain.Cidade;
import com.abs.cursomc.domain.Cliente;
import com.abs.cursomc.domain.Endereco;
import com.abs.cursomc.domain.Estado;
import com.abs.cursomc.domain.ItemPedido;
import com.abs.cursomc.domain.Pagamento;
import com.abs.cursomc.domain.PagamentoComBoleto;
import com.abs.cursomc.domain.PagamentoComCartao;
import com.abs.cursomc.domain.Pedido;
import com.abs.cursomc.domain.Produto;
import com.abs.cursomc.domain.enums.EstadoPagamento;
import com.abs.cursomc.domain.enums.TipoCliente;
import com.abs.cursomc.repositories.CategoriaRepository;
import com.abs.cursomc.repositories.CidadeRepository;
import com.abs.cursomc.repositories.ClienteRepository;
import com.abs.cursomc.repositories.EnderecoRepository;
import com.abs.cursomc.repositories.EstadoRepository;
import com.abs.cursomc.repositories.ItemPedidoRepository;
import com.abs.cursomc.repositories.PagamentoRepository;
import com.abs.cursomc.repositories.PedidoRepository;
import com.abs.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 4000.00);
		Produto p2 = new Produto(null, "Impressora", 200.00);
		Produto p3 = new Produto(null, "Mouse", 35.00);
		Produto p4 = new Produto(null, "Mesa", 300.00);
		Produto p5 = new Produto(null, "Toalha", 30.00);
		Produto p6 = new Produto(null, "Colcha", 40.00);
		Produto p7 = new Produto(null, "TV", 2000.00);
		Produto p8 = new Produto(null, "Outro", 120.00);
		Produto p9 = new Produto(null, "Abajoiur", 800.00);
		Produto p10 = new Produto(null, "Pendente", 180.00);
		Produto p11 = new Produto(null, "Shampoo", 90.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1,p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9,p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
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
		
		Estado est1 = new Estado(null, "MG");
		Estado est2 = new Estado(null, "SP");
		
		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "nome 1", "email1@gmail.xom", "12345",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("344555","776666"));
		
		Endereco end1  = new Endereco(null, "rua kmdks", "333", "complemento", "bairro", "98888", cli1, cid1);
		Endereco end2  = new Endereco(null, "rua kmdks yyyyy", "877", "completo", "bairro 5555", "98888", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Pedido ped1 = new Pedido(null, sdf.parse("30/03/2017 00:30"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 13:24"), cli1, end2);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.Quitado, ped1, 6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.Pendente, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		
		ItemPedido it1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido it2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido it3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
		
		p1.getItens().addAll(Arrays.asList(it1, it2));
		p2.getItens().addAll(Arrays.asList(it3));
		
		p1.getItens().addAll(Arrays.asList(it1));
		p2.getItens().addAll(Arrays.asList(it3));
		p3.getItens().addAll(Arrays.asList(it2));
		
		itemPedidoRepository.saveAll(Arrays.asList(it1, it2, it3));
	}
}
