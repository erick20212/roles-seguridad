package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Linea;
import com.example.security.repository.LineaRepository;
import com.example.security.service.LineaService;

@Service
public class LineaServiceImpl implements LineaService{

	@Autowired
	private LineaRepository repository;
	@Override
	public Linea create(Linea a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Linea update(Linea a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}

	@Override
	public Optional<Linea> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Linea> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
