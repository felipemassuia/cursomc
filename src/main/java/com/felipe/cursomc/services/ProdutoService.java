package com.felipe.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.Categoria;
import com.felipe.cursomc.domain.Produto;
import com.felipe.cursomc.repositories.CategoriaRepository;
import com.felipe.cursomc.repositories.ProdutoRepository;
import com.felipe.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> cat = repo.findById(id);
		// return cat.orElse(null);

		return cat.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));

	}

	public Page<Produto> search(String nome, List<Integer> idsCategorias, Integer page,
		Integer linesPerPage,
		String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
			Direction.valueOf(direction), orderBy);
			
		List<Categoria> categorias = categoriaRepository.findAllById(idsCategorias);
		
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);	
	}
 
}
