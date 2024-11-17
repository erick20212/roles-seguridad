package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Accesos;
import com.example.security.repository.AccesosRepository;
import com.example.security.service.AccesoService;

@Service
public class AccesoServiceImpl implements AccesoService{

	@Autowired
	private AccesosRepository  repository;
	@Override
	public Accesos create(Accesos a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Accesos update(Accesos a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}

	@Override
	public Optional<Accesos> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Accesos> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
