package com.abs.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.abs.cursomc.domain.Cliente;
import com.abs.cursomc.domain.enums.TipoCliente;
import com.abs.cursomc.dto.ClienteNewDTO;
import com.abs.cursomc.repositories.ClienteRepository;
import com.abs.cursomc.resources.exception.FieldMessage;
import com.abs.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipoCliente() == null) {
			list.add(new FieldMessage("TipoCliente", "Não pode ser nulo"));
		}
		
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) &&
				!BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CPF inválido"));
		}
		
		if (objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) &&
				!BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("CpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente cli = repo.findByEmail(objDto.getEmail());
		if (cli != null) {
			list.add(new FieldMessage("Email", "e-mail já cadastrado"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
