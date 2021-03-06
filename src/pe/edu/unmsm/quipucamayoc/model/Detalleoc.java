// default package
// Generated 16/06/2014 05:57:43 PM by Hibernate Tools 3.4.0.CR1
package pe.edu.unmsm.quipucamayoc.model;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.faces.event.ValueChangeEvent;

import org.springframework.context.annotation.Scope;

/**
 * Detalleoc generated by hbm2java
 */
public class Detalleoc implements java.io.Serializable {

	private DetalleocId id;
	private Ordencompra ordencompra;
	private String especifica;
	private Character impuesto;
	private String artcod;
	private String marca;
	private char moneda;
	private BigDecimal cantidad;
	private BigDecimal preciounit;
	private char tipoprecio;
	private BigDecimal subtotal;
	private BigDecimal igv;
	private String artdes;
	private String detalle;
	private String unidmed;
	private String ocnro;//Foránea de Orden compra

	public Detalleoc() {
	}

	public Detalleoc(DetalleocId id, Ordencompra ordencompra) {
		this.id = id;
		this.ordencompra = ordencompra;
	}

	public Detalleoc(DetalleocId id, Ordencompra ordencompra,
			String especifica, Character impuesto, String artcod, String marca,
			char moneda, BigDecimal cantidad, BigDecimal preciounit,
			char tipoprecio, BigDecimal subtotal, BigDecimal igv,
			String artdes,String unidmed ,String detalle) {
		this.id = id;
		this.ordencompra = ordencompra;
		this.especifica = especifica;
		this.impuesto = impuesto;
		this.artcod = artcod;
		this.marca = marca;
		this.moneda = moneda;
		this.cantidad = cantidad;
		this.preciounit = preciounit;
		this.tipoprecio = tipoprecio;
		this.subtotal = subtotal;
		this.igv = igv;
		this.artdes = artdes;
		this.detalle = detalle;
		this.unidmed=unidmed;
	}

	public DetalleocId getId() {
		return this.id;
	}

	public void setId(DetalleocId id) {
		this.id = id;
	}

	public Ordencompra getOrdencompra() {
		return this.ordencompra;
	}

	public void setOrdencompra(Ordencompra ordencompra) {
		this.ordencompra = ordencompra;
	}

	public String getEspecifica() {
		return this.especifica;
	}

	public void setEspecifica(String especifica) {
		this.especifica = especifica;
	}

	public Character getImpuesto() {
		return this.impuesto;
	}

	public void setImpuesto(Character impuesto) {
		this.impuesto = impuesto;
	}

	public String getArtcod() {
		return this.artcod;
	}

	public void setArtcod(String artcod) {
		this.artcod = artcod;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public char getMoneda() {
		return this.moneda;
	}

	public void setMoneda(char moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPreciounit() {
		return this.preciounit;
	}

	public void setPreciounit(BigDecimal preciounit) {
		this.preciounit = preciounit;
	}

	public char getTipoprecio() {
		return this.tipoprecio;
	}

	public void setTipoprecio(char tipoprecio) {
		this.tipoprecio = tipoprecio;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIgv() {
		return this.igv;
	}

	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}

	public String getArtdes() {
		return this.artdes;
	}

	public void setArtdes(String artdes) {
		this.artdes = artdes;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getOcnro() {
		return ocnro;
	}

	public void setOcnro(String ocnro) {
		this.ocnro = ocnro;
	}

	public String getUnidmed() {
		return unidmed;
	}

	public void setUnidmed(String unidmed) {
		this.unidmed = unidmed;
	}
	/*Funciones Adicionales*/
	public void calcularMontoEnFuncionDeSeleccionIGV(ValueChangeEvent event){
		//Calcula segun la seleccion del desplegable Elija impuesto.
		BigDecimal MontoSinIGV = new BigDecimal("0.00");
		if(this.cantidad!=null&&this.preciounit!=null){
			MontoSinIGV = this.cantidad.multiply(this.preciounit);
			Character valor = (Character)event.getNewValue();
			BigDecimal conIgvAdicional = new BigDecimal("1.18");
			BigDecimal igv = new BigDecimal("0.18");
			if(valor=='2'){      
				BigDecimal auxMonto = redondearIGV(MontoSinIGV.multiply(conIgvAdicional));
				BigDecimal auxIGV = redondearIGV(MontoSinIGV.multiply(igv));
				this.setImpuesto('2');
				this.setIgv(auxIGV);
				this.setSubtotal(auxMonto);				
			} else if(valor=='1'){
				BigDecimal aux = MontoSinIGV;
				BigDecimal auxMonto = redondearIGV(aux.divide(conIgvAdicional,9,RoundingMode.HALF_UP));
				BigDecimal auxIGV = redondearIGV(auxMonto.multiply(igv));
				MontoSinIGV = redondearIGV(MontoSinIGV);
				this.setImpuesto('1');
				this.setIgv(auxIGV);
				this.setSubtotal(MontoSinIGV);				
			}
		}		
	}	
	public void calcularMontoEnFuncionDeCantidad(ValueChangeEvent event){
		//Calcula segun la inserción de montos en las cajas de texto cantidad y precio unitario.
		BigDecimal MontoSinIGV = new BigDecimal("0.00");
		if(this.cantidad!=null&&this.preciounit!=null){
			BigDecimal valor=(BigDecimal)event.getNewValue();
			MontoSinIGV = valor.multiply(this.preciounit);			
			BigDecimal conIgvAdicional = new BigDecimal("1.18");
			BigDecimal igv = new BigDecimal("0.18");
			if(this.impuesto=='2'){      
				BigDecimal auxMonto = redondearIGV(MontoSinIGV.multiply(conIgvAdicional));
				BigDecimal auxIGV = redondearIGV(MontoSinIGV.multiply(igv));
				this.setImpuesto('2');
				this.setIgv(auxIGV);
				this.setSubtotal(auxMonto);				
			} else if(this.impuesto=='1'){
				BigDecimal aux = MontoSinIGV;
				BigDecimal auxMonto = redondearIGV(aux.divide(conIgvAdicional,9,RoundingMode.HALF_UP));
				BigDecimal auxIGV = redondearIGV(auxMonto.multiply(igv));
				MontoSinIGV = redondearIGV(MontoSinIGV);
				this.setImpuesto('1');
				this.setIgv(auxIGV);
				this.setSubtotal(MontoSinIGV);				
			}
		}		
	}	
	public void calcularMontoEnFuncionDePrecioUnit(ValueChangeEvent event){
		//Calcula segun la inserción de montos en las cajas de texto cantidad y precio unitario.
		BigDecimal MontoSinIGV = new BigDecimal("0.00");
		if(this.cantidad!=null&&this.preciounit!=null){
			BigDecimal valor=(BigDecimal)event.getNewValue();
			MontoSinIGV = this.cantidad.multiply(valor);			
			BigDecimal conIgvAdicional = new BigDecimal("1.18");
			BigDecimal igv = new BigDecimal("0.18");
			if(this.impuesto=='2'){      
				BigDecimal auxMonto = redondearIGV(MontoSinIGV.multiply(conIgvAdicional));
				BigDecimal auxIGV = redondearIGV(MontoSinIGV.multiply(igv));
				this.setImpuesto('2');
				this.setIgv(auxIGV);
				this.setSubtotal(auxMonto);				
			} else if(this.impuesto=='1'){
				BigDecimal aux = MontoSinIGV;
				BigDecimal auxMonto = redondearIGV(aux.divide(conIgvAdicional,9,RoundingMode.HALF_UP));
				BigDecimal auxIGV = redondearIGV(auxMonto.multiply(igv));
				MontoSinIGV = redondearIGV(MontoSinIGV);
				this.setImpuesto('1');
				this.setIgv(auxIGV);
				this.setSubtotal(MontoSinIGV);				
			}
		}		
	}	
	public boolean convertImpuestoToBoolean(){
		if(this.impuesto == '1'){
			return true;
		}		
		else if(this.impuesto == '2'){
			return false;
		}else{
			return false;
		}
	}
	private BigDecimal redondearIGV(BigDecimal igv){
		
		BigDecimal redondeado =igv.setScale(2, BigDecimal.ROUND_HALF_UP);		
		return redondeado;
	}
	@Override
	public String toString() {
		return "Detalleoc [id=" + id + ", ordencompra=" + ordencompra
				+ ", especifica=" + especifica + ", impuesto=" + impuesto
				+ ", artcod=" + artcod + ", marca=" + marca + ", moneda="
				+ moneda + ", cantidad=" + cantidad + ", preciounit="
				+ preciounit + ", tipoprecio=" + tipoprecio + ", subtotal="
				+ subtotal + ", igv=" + igv + ", artdes=" + artdes
				+ ", detalle=" + detalle + ", unidmed=" + unidmed + ", ocnro="
				+ ocnro + "]";
	}
	
	
}
