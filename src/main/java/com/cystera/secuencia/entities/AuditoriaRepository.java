package com.cystera.secuencia.entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface AuditoriaRepository extends CrudRepository<Auditoria, Long> {

	
	@Query("select new com.cystera.secuencia.entities.Auditoria(a.id, a.fecha, a.operacion,  a.estadoOperacion,a.descripcionResultado) from Auditoria a ")
	List<Auditoria> findByAll();
}
