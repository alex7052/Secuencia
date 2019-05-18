package com.cystera.secuencia.entities;

public class Respuesta {

	//Atributos de la clase
	
	/**
	 * estado de la respuesta
	 */
	private String estado;
	
	/**
	 * Descripcion de la respuesta
	 */
	private String descripcion;
	
	//Constrcutores de la clase

	/**
	 * Constrcutor sin parametros
	 */
	public Respuesta() {
		this.estado = "";
		this.descripcion = "";
	}
	
	/**
	 * Constrcutor con parametros
	 * @param estado
	 * @param descripcion
	 */
	public Respuesta(String estado, String descripcion) {
		this.estado = estado;
		this.descripcion = descripcion;
	}

	
	//Metodos de la clase
	
	/**
	 * retorna el estado de la respuesta
	 * @return
	 */
	public String getEstado() {
		return this.estado;
	}

	/**
	 * Setea el estado de la respuesta
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * retorna la descripcion de la respuesta
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * setea la descripcion de la respuesta
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
	
}
