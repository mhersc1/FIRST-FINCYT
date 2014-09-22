package pe.edu.unmsm.quipucamayoc.web.mb;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
@Controller
@Scope("session")
public class OrdenFiltroMB implements Serializable{


private String numeroFilter		="";
private String rsocialFilter	="";
private String rucFilter		="";
private Date fechaFilter		;		
private Integer estadoFilter	;
private Long numeroPFilter	;
private String nroExp			="";
private String factura			="";


public String getOcnroFiltro() {
	return numeroFilter;
}

public void setOcnroFiltro(String numeroFilter) {
	this.numeroFilter = numeroFilter;
}

public String getNumeroFilter() {
	return numeroFilter;
}

public void setNumeroFilter(String numeroFilter) {
	this.numeroFilter = numeroFilter;
}

public String getRsocialFilter() {
	return rsocialFilter;
}

public void setRsocialFilter(String rsocialFilter) {
	this.rsocialFilter = rsocialFilter;
}

public String getRucFilter() {
	return rucFilter;
}

public void setRucFilter(String rucFilter) {
	this.rucFilter = rucFilter;
}

public Date getFechaFilter() {
	return fechaFilter;
}

public void setFechaFilter(Date fechaFilter) {
	this.fechaFilter = fechaFilter;
}

public Integer getEstadoFilter() {
	return estadoFilter;
}

public void setEstadoFilter(Integer estadoFilter) {
	this.estadoFilter = estadoFilter;
}

public Long getNumeroPFilter() {
	return numeroPFilter;
}

public void setNumeroPFilter(Long numeroPFilter) {
	this.numeroPFilter = numeroPFilter;
}

public String getNroExp() {
	return nroExp;
}

public void setNroExp(String nroExp) {
	this.nroExp = nroExp;
}

public String getFactura() {
	return factura;
}

public void setFactura(String factura) {
	this.factura = factura;
}



}
