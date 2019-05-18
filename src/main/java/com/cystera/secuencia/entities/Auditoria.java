package com.cystera.secuencia.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Auditoria {
	
	//Atributos de la clase
	
	/**
	 * Identificador de auditoria
	 */
	@Id
	@GeneratedValue
	private long id;
	
	/**
	 * fecha de registro
	 */
	private LocalDateTime fecha;
	
	/**
	 * Operacion solicitada
	 */
	private String operacion;
	
	/**
	 * estado de la Operacion solicitada
	 */
	private String estadoOperacion;
	
	/**
	 * Descripcion del resultado de la operacion 
	 */
	private String descripcionResultado;

	
	//Constructores de la clase
	
	/**
	 * Constrcutor sin parametros
	 */
	public Auditoria() {
		this.id = 0;
		this.fecha = LocalDateTime.now();
		this.operacion = "";
		this.estadoOperacion = "";
		this.descripcionResultado = "";
	}
	
	/**
	 * Constrcutor con parametros
	 * @param id
	 * @param fecha
	 * @param operacion
	 * @param estadoOperacion
	 * @param descripcionResultado
	 */
	public Auditoria(long id, LocalDateTime fecha, String operacion, String estadoOperacion, String descripcionResultado) {
		this.id = id;
		this.fecha = fecha;
		this.operacion = operacion;
		this.estadoOperacion = estadoOperacion;
		this.descripcionResultado = descripcionResultado;
	}

	//Metodos de la clase
	
	/**
	 * retorna id de la auditoria
	 * @return
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * setea id de la auditoria
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * retorna fecha de la auditoria
	 * @return
	 */
	public LocalDateTime getFecha() {
		return this.fecha;
	}

	/**
	 * setea fecha de la audtoria
	 * @param fecha
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * retorna la operacion solicitada
	 * @return
	 */
	public String getOperacion() {
		return this.operacion;
	}

	/**
	 * setea la operacion solicitada
	 * @param operacion
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	/**
	 * retorna estado de la operacion
	 * @return
	 */
	public String getEstadoOperacion() {
		return estadoOperacion;
	}

	/**
	 * setea estado de la operacion
	 * @param estadoOperacion
	 */
	public void setEstadoOperacion(String estadoOperacion) {
		this.estadoOperacion = estadoOperacion;
	}

	/**
	 * retorna descripcion del resultado de la operacion
	 * @return
	 */
	public String getDescripcionResultado() {
		return descripcionResultado;
	}

	/**
	 * setea descripcion del resultado de la operacion
	 * @param descripcionResultado
	 */
	public void setDescripcionResultado(String descripcionResultado) {
		this.descripcionResultado = descripcionResultado;
	}
	
	
	
	
	

}
