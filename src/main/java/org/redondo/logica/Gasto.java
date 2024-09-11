package org.redondo.logica;

import java.util.Date;

public class Gasto {

	private int id;
	private double cantidad;
	private String categoria, descripcion;
	private Date fecha;

	public Gasto(int id, double cantidad, String categoria, String descripcion, Date fecha) {
		this.id = id;
		this.cantidad = cantidad;
		this.categoria = categoria;
		this.descripcion = descripcion;
		this.fecha = fecha;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
