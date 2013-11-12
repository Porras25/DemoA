package com.enpoints;

import javax.persistence.Entity;
import javax.persistence.*;
@Entity
public class Fruta {
	
	@Id
	private String Id;
	private String Descripcion;
	
	private String Nombre;
	public Fruta(){
		
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}	

}
