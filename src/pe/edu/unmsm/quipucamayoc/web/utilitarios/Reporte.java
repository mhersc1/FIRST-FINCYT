package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.util.Collection;
import java.util.Map;

public class Reporte {
private String nombreReporte;
private Map<String,Object> parametrosReporte;
private Collection<? extends Object> camposReporte;
public Reporte() {
	super();
	// TODO Auto-generated constructor stub
}
public String getNombreReporte() {
	return nombreReporte;
}
public void setNombreReporte(String nombreReporte) {
	this.nombreReporte = nombreReporte;
}
public Map<String, Object> getParametrosReporte() {
	return parametrosReporte;
}
public void setParametrosReporte(Map<String, Object> parametrosReporte) {
	this.parametrosReporte = parametrosReporte;
}
public Collection<? extends Object> getCamposReporte() {
	return camposReporte;
}
public void setCamposReporte(Collection<? extends Object> camposReporte) {
	this.camposReporte = camposReporte;
}
}
