package com.sistweb.curso.services.validation;


import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistweb.curso.domain.Cliente;
import com.sistweb.curso.domain.enums.TipoCliente;
import com.sistweb.curso.dto.ClienteNewDTO;
import com.sistweb.curso.repositories.ClienteRepository;
import com.sistweb.curso.resources.exception.FieldMessage;
import com.sistweb.curso.services.validation.utils.BR; 

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
 
@Autowired
	private ClienteRepository repo;

@Override
	public void initialize(ClienteInsert ann) {
	}
  
@Override

    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>(); 
        
        // inclua os testes aqui, inserindo erros na lista
        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
        	list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }
        
        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
        	list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }
        
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null) {
        	list.add(new FieldMessage("Email", "Email inválido"));
        }
     
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
            .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}

