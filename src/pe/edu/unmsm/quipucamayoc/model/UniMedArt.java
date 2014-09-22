package pe.edu.unmsm.quipucamayoc.model;


/**
 * UniMedArt entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UniMedArt implements java.io.Serializable {

	// Fields

	private String unimedcod;
	private String unimedabr;
	private String unimeddes;
	private String unimedest;
	private Long unicodaux;

	// Constructors

	/** default constructor */
	public UniMedArt() {
	}

	/** minimal constructor */
	public UniMedArt(String unimedcod) {
		this.unimedcod = unimedcod;
	}

	/** full constructor */
	public UniMedArt(String unimedcod, String unimedabr, String unimeddes,
			String unimedest, Long unicodaux) {
		this.unimedcod = unimedcod;
		this.unimedabr = unimedabr;
		this.unimeddes = unimeddes;
		this.unimedest = unimedest;
		this.unicodaux = unicodaux;
	}

	// Property accessors

	public String getUnimedcod() {
		return this.unimedcod;
	}

	public void setUnimedcod(String unimedcod) {
		this.unimedcod = unimedcod;
	}

	public String getUnimedabr() {
		return this.unimedabr;
	}

	public void setUnimedabr(String unimedabr) {
		this.unimedabr = unimedabr;
	}

	public String getUnimeddes() {
		return this.unimeddes;
	}

	public void setUnimeddes(String unimeddes) {
		this.unimeddes = unimeddes;
	}

	public String getUnimedest() {
		return this.unimedest;
	}

	public void setUnimedest(String unimedest) {
		this.unimedest = unimedest;
	}

	public Long getUnicodaux() {
		return this.unicodaux;
	}

	public void setUnicodaux(Long unicodaux) {
		this.unicodaux = unicodaux;
	}
}