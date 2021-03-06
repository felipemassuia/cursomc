package com.felipe.cursomc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.Categoria;
import com.felipe.cursomc.dto.CategoriaDTO;
import com.felipe.cursomc.repositories.CategoriaRepository;
import com.felipe.cursomc.services.exceptions.DataIntegrityException;
import com.felipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional <Categoria> cat = repo.findById(id);
		//return cat.orElse(null);
		
		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
	}

	public Categoria insert(Categoria obj) {
		
		obj.setId(null);
		
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		Categoria objBanco = find(obj.getId());
		updateData(obj, objBanco);
		return repo.save(obj);
	}

	private void updateData(Categoria obj, Categoria objBanco) {
		objBanco.setNome(obj.getNome());
	}

	public void delete(Integer id) {
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que tenha produtos!");
		}
	}

	public List<Categoria> findAll() {
		
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDTO(CategoriaDTO objDto) {
		
		return new Categoria(objDto.getId(), objDto.getNome());
	}
	
	
}
