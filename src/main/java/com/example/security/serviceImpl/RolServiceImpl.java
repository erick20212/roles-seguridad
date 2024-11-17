package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Rol;
import com.example.security.repository.RolRepository;
import com.example.security.service.RolService;

@Service
public class RolServiceImpl implements RolService{

	@Autowired
	private RolRepository repository;
	@Override
	public Rol create(Rol a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Rol update(Rol a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}

	@Override
	public Optional<Rol> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Rol> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
