package pe.edu.unmsm.quipucamayoc.model;
// default package
// Generated 21/07/2014 12:26:43 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * PecosaId generated by hbm2java
 */
public class PecosaId implements java.io.Serializable {

	private Long pecnro;
	private int proyectoid;

	public PecosaId() {
	}

	public PecosaId(Long pecnro, int proyectoid) {
		this.pecnro = pecnro;
		this.proyectoid = proyectoid;
	}

	public Long getPecnro() {
		return this.pecnro;
	}

	public void setPecnro(Long pecnro) {
		this.pecnro = pecnro;
	}

	public int getProyectoid() {
		return this.proyectoid;
	}

	public void setProyectoid(int proyectoid) {
		this.proyectoid = proyectoid;
	}

}
