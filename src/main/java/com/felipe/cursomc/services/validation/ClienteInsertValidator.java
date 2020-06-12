package com.felipe.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;

import com.felipe.cursomc.domain.Cliente;
import com.felipe.cursomc.domain.enums.TipoCliente;
import com.felipe.cursomc.dto.ClienteNewDTO;
import com.felipe.cursomc.repositories.ClienteRepository;
import com.felipe.cursomc.resources.exceptions.FieldMessage;
import com.felipe.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente clienteEncontrado = clienteRepository.findByEmail(objDto.getEmail());
		
		if(clienteEncontrado != null) {
			list.add(new FieldMessage("email", "Email já está em uso"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}