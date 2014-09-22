package pe.edu.unmsm.quipucamayoc.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Marca entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Marca implements java.io.Serializable {

	// Fields

	private Long marcod;
	private String mardes;

	// Constructors

	/** default constructor */
	public Marca() {
	}

	/** minimal constructor */
	public Marca(Long marcod) {
		this.marcod = marcod;
	}

	/** full constructor */
	public Marca(Long marcod, String mardes) {
		this.marcod = marcod;
		this.mardes = mardes;
	}

	// Property accessors

	public Long getMarcod() {
		return this.marcod;
	}

	public void setMarcod(Long marcod) {
		this.marcod = marcod;
	}

	public String getMardes() {
		return this.mardes;
	}

	public void setMardes(String mardes) {
		this.mardes = mardes;
	}
}