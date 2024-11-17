package com.example.security.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Empresa;
import com.example.security.repository.EmpresaRepository;
import com.example.security.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaRepository repository;
	@Override
	public Empresa create(Empresa a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public Empresa update(Empresa a) {
		// TODO Auto-generated method stub
		return repository.save(a);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
		
	}

	@Override
	public Optional<Empresa> read(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<Empresa> readAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
