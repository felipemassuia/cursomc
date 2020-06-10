package com.felipe.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.Categoria;
import com.felipe.cursomc.domain.Cliente;
import com.felipe.cursomc.repositories.ClienteRepository;
import com.felipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Optional <Cliente> cat = repo.findById(id);
		//return cat.orElse(null);
		
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
	
}
