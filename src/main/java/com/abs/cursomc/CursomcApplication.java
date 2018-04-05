package com.abs.cursomc;

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
import com.abs.cursomc.domain.Produto;
import com.abs.cursomc.domain.enums.TipoCliente;
import com.abs.cursomc.repositories.CategoriaRepository;
import com.abs.cursomc.repositories.CidadeRepository;
import com.abs.cursomc.repositories.ClienteRepository;
import com.abs.cursomc.repositories.EnderecoRepository;
import com.abs.cursomc.repositories.EstadoRepository;
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 200.00);
		Produto p3 = new Produto(null, "mouse", 30.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
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
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		clienteRepository.save(cli1);
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
	}
}
