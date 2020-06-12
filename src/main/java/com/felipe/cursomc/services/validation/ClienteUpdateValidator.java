package com.felipe.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.felipe.cursomc.domain.Cliente;
import com.felipe.cursomc.domain.enums.TipoCliente;
import com.felipe.cursomc.dto.ClienteDTO;
import com.felipe.cursomc.dto.ClienteNewDTO;
import com.felipe.cursomc.repositories.ClienteRepository;
import com.felipe.cursomc.resources.exceptions.FieldMessage;
import com.felipe.cursomc.services.validation.utils.BR;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	ClienteRepository clienteRepository;

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();
		
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Cliente clienteEncontrado = clienteRepository.findByEmail(objDto.getEmail());
		
		if(clienteEncontrado != null && ! (clienteEncontrado.getId() == Integer.parseInt(map.get("id")))) {
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