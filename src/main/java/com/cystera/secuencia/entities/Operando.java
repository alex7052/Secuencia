package com.cystera.secuencia.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Operando {
	
	//Atributos de la clase
	
	/**
	 * identificador del operando
	 */
	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * valor del operando
	 */
	private Double numero;
	
	/**
	 * Instancia de secuencia
	 */
	@ManyToOne
	@JoinColumn(name = "idSecuencia", nullable = false )
	private Secuencia secuencia;

	//Constrcutores de la clase
	
	/**
	 * Constrcutor sin parametros
	 * @param id
	 * @param numero
	 */
	public Operando() {
		this.id = 0;
		this.numero = 0D;
		this.secuencia=new Secuencia();
	}
	
	/**
	 * Constructor con parametros
	 * @param id
	 * @param numero
	 */
	public Operando(int id, Double numero, Secuencia secuencia) {
		this.id = id;
		this.numero = numero;
		this.secuencia=secuencia;
	}

	//Metodos de la clase
	
	/**
	 * retorna identificador de operando
	 * @return
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * setea identificador de operando
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Retorna valor de operando
	 * @return
	 */
	public Double getNumero() {
		return numero;
	}
	
	/**
	 * setea valor de operando
	 * @param numero
	 */
	public void setNumero(Double numero) {
		this.numero = numero;
	}

	/**
	 * Retorna el valor de la secuencia
	 * @return
	 */
	public Secuencia getSecuencia() {
		return this.secuencia;
	}

	/**
	 * setea el valor de la secuencia
	 * @param secuencia
	 */
	public void setSecuencia(Secuencia secuencia) {
		this.secuencia = secuencia;
	}
	
	
	
	
	

}
