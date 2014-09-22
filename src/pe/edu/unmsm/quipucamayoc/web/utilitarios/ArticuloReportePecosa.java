package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.math.BigDecimal;

public class ArticuloReportePecosa {

	private String idArticulo;//
	private Long cantidad;
	private String descripcion;
	private BigDecimal precioUnit;
	private BigDecimal precioTotal;
	private String uniMed;
	private String marca;
	private String modelo;
	private String moneda;
	private String tipoPrecio;
	private String tipoCambio;
	private String precioUnitOrig;//precio original
	private String caracteristicas;
	private String precioTotalAux;//Auxiliar
	//servicios
	private String igv;
	private String detalle;
	
	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public ArticuloReportePecosa() {
		super();
	}
	
	public String getIdArticulo() {
		return idArticulo;
	}
	public void setIdArticulo(String idArticulo) {
		this.idArticulo = idArticulo;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecioUnit() {
		return precioUnit;
	}
	public void setPrecioUnit(BigDecimal precioUnit) {
		this.precioUnit = precioUnit;
	}
	public String getUniMed() {
		return uniMed;
	}
	public void setUniMed(String uniMed) {
		this.uniMed = uniMed;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTipoPrecio() {
		return tipoPrecio;
	}

	public void setTipoPrecio(String tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}


	public String getPrecioUnitOrig() {
		return precioUnitOrig;
	}

	public void setPrecioUnitOrig(String precioUnitOrig) {
		this.precioUnitOrig = precioUnitOrig;
	}

	public String getIgv() {
		return igv;
	}

	public void setIgv(String igv) {
		this.igv = igv;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getPrecioTotalAux() {
		return precioTotalAux;
	}

	public void setPrecioTotalAux(String precioTotalAux) {
		this.precioTotalAux = precioTotalAux;
	}


	
}
