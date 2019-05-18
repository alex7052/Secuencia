package com.cystera.secuencia.entities;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SecuenciaRepository extends CrudRepository<Secuencia, Long> {
	
	@Query("select new com.cystera.secuencia.entities.Secuencia(s.id, s.descripcion) from Secuencia s ")
	List<Secuencia> findByAll();

}
