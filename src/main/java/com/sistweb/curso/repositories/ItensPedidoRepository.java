package com.sistweb.curso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistweb.curso.domain.ItemPedido;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer> {
	

}
