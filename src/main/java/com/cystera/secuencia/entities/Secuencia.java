package com.cystera.secuencia.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Secuencia {
	
	//Atributos de la clase
	
	/**
	 * Identificador de secuencia
	 */
	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * Descripcion de secuencia
	 */
	private String descripcion;
	
	/**
	 * Conjunto de operandos
	 */
	@OneToMany( cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "secuencia" )
	private Collection<Operando> operandos;
	
	
	//Constrcutores de la clase
	
	/**
	 * Constrcutor sin parametros
	 */
	public Secuencia() {
		this.id = 0;
		this.descripcion = "";
		this.operandos= new ArrayList<Operando>();
	}
	
	/**
	 * Constrcutor con parametros
	 * @param id
	 * @param descripcion
	 */
	public Secuencia(int id, String descripcion, ArrayList<Operando> operandos) {
		this.id = id;
		this.descripcion = descripcion;
		this.operandos = operandos;
	}

	//Metdodos de la clase
	
	/**
	 * retorna el valor de identificador
	 * @return
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Setea valor de identificador
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna valor de identificador
	 * @return
	 */
	public String getDescripcion() {
		return this.descripcion;
	}
	
	/**
	 * setea valor del identificador
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * retorna valor de los operandos
	 * @return
	 */
	public Collection<Operando> getOperandos() {
		return this.operandos;
	}

	/**
	 * Setea valor de los operandos
	 * @param operandos
	 */
	public void setOperandos(Collection<Operando> operandos) {
		this.operandos = operandos;
	}
	
	
	
	

}
