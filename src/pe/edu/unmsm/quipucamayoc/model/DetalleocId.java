// default package
// Generated 16/06/2014 05:57:43 PM by Hibernate Tools 3.4.0.CR1
package pe.edu.unmsm.quipucamayoc.model;
import java.math.BigDecimal;

/**
 * DetalleocId generated by hbm2java
 */
public class DetalleocId implements java.io.Serializable {

	private Long detocid;
	private int proyectoid;

	public DetalleocId() {
	}

	public DetalleocId(Long detocid, int proyectoid) {
		this.detocid = detocid;
		this.proyectoid = proyectoid;
	}

	public Long getDetocid() {
		return this.detocid;
	}

	public void setDetocid(Long detocid) {
		this.detocid = detocid;
	}

	public int getProyectoid() {
		return this.proyectoid;
	}

	public void setProyectoid(int proyectoid) {
		this.proyectoid = proyectoid;
	}
}
