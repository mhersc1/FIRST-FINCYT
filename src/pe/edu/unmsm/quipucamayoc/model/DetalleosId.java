// default package
// Generated 16/06/2014 05:57:43 PM by Hibernate Tools 3.4.0.CR1
package pe.edu.unmsm.quipucamayoc.model;
import java.math.BigDecimal;

/**
 * DetalleosId generated by hbm2java
 */
public class DetalleosId implements java.io.Serializable {

	private Long detosid;
	private int proyectoid;

	public DetalleosId() {
	}

	public DetalleosId(Long detosid, int proyectoid) {
		this.detosid = detosid;
		this.proyectoid = proyectoid;
	}

	public Long getDetosid() {
		return this.detosid;
	}

	public void setDetosid(Long detosid) {
		this.detosid = detosid;
	}

	public int getProyectoid() {
		return this.proyectoid;
	}

	public void setProyectoid(int proyectoid) {
		this.proyectoid = proyectoid;
	}

}
