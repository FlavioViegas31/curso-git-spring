package com.sistweb.curso.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistweb.curso.domain.Cidade;
import com.sistweb.curso.domain.Cliente;
import com.sistweb.curso.domain.Endereco;
import com.sistweb.curso.domain.enums.TipoCliente;
import com.sistweb.curso.dto.ClienteDTO;
import com.sistweb.curso.dto.ClienteNewDTO;
import com.sistweb.curso.repositories.ClienteRepository;
import com.sistweb.curso.repositories.EnderecoRepository;
import com.sistweb.curso.services.exception.DataIntegrityException;
import com.sistweb.curso.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
	 Optional<Cliente> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id"  + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
		
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não e possível excluir porque a entidade pedido relacionadas");
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid); 
				cli.getEnderecos().add(end); 
				cli.getTelefones().add(objDto.getTelefone01());
				if (objDto.getTelefone02()!=null) {
					cli.getTelefones().add(objDto.getTelefone02());
				}
				if (objDto.getTelefone03()!=null) {
					cli.getTelefones().add(objDto.getTelefone03());
				}
				return cli;
		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
