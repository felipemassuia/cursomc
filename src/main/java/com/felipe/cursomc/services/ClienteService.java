package com.felipe.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.Cliente;
import com.felipe.cursomc.dto.ClienteDTO;
import com.felipe.cursomc.repositories.ClienteRepository;
import com.felipe.cursomc.services.exceptions.DataIntegrityException;
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

	public Cliente insert(Cliente obj) {
		
		obj.setId(null);
		
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) {
		Cliente clienteBanco = find(obj.getId());
		updateData(obj, clienteBanco);
		return repo.save(clienteBanco);
	}

	private void updateData(Cliente obj, Cliente clienteBanco) {
		clienteBanco.setNome(obj.getNome());
		clienteBanco.setEmail(obj.getEmail());
		
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que tenha relações!");
		}
	}

	public List<Cliente> findAll() {
		
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
}
