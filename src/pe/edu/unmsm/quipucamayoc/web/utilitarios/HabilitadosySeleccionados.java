package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;

public class HabilitadosySeleccionados implements Serializable {

private String 	provtab 	= 	"provtab1";
private String 	arttab  	= 	"arttab1";
private final String 	listadoOC	=	"listaOCAlt";
private boolean buscarProv	= 	false;	//Si hizo click en el boton Buscar::Proveedor
private boolean buscarArt	=	false;	//Si hizo click en el boton Buscar::Articulo
private boolean buscarOC	=	false;
private int 	modoBusqProv=	0;		//Filtrar por: RadioButton 0:RUC 1:NOMBRE
private int cantProveedores;
private int cantArticulos;
private int cantOCs;
private int cantOCsConsult;
private int ocestado;
private int cantOSs;
private int cantOSsConsult;
private int osestado;
private int cantPecosas;
private int cantPecosasConsult;
private int pecestado;

//***Resultado de búsquedas
private ArrayList<Proveedor> proveedores;
private ArrayList<Articulo>  articulos;

public Map<Integer,String> FormatFINCyt(){
	Map <Integer,String> contratos=new HashMap<Integer,String>();
	/**
	 * Concursos Fincyt
	 * pOR Codigo de contratoS
	 */
	/**
	 * key:proyectoid
	 * llave:contrato Nº
	 */
	contratos.put(1, "131-");
	contratos.put(2, "132-");
	contratos.put(3, "133-");
	contratos.put(4, "134-");
	contratos.put(5, "135-");
	contratos.put(6, "173-");
	contratos.put(7, "180-");
	contratos.put(8, "186-");
	contratos.put(9, "188-");
	contratos.put(10,"230-");
	contratos.put(11,"238-");	
	return contratos;
}
public int getCantProveedores(){
	return cantProveedores;
}

public void setCantProveedores(int cantidad){
	this.cantProveedores = cantidad;
}

public int getCantArticulos(){
	return cantArticulos;
}

public void setCantArticulos(int cantidad){
	this.cantArticulos=cantidad;
}

public String getProvtab() {
	return provtab;
}
public void setProvtab(String provtab) {
	this.provtab = provtab;
}
public String getArttab() {
	return arttab;
}
public void setArttab(String arttab) {
	this.arttab = arttab;
}
public boolean isBuscarProv() {
	return buscarProv;
}
public void setBuscarProv(boolean buscarProv) {
	this.buscarProv = buscarProv;
}
public int getModoProv() {
	return modoBusqProv;
}
public void setModoProv(int modoBusqProv) {
	this.modoBusqProv = modoBusqProv;
}
public boolean isBuscarArt() {
	return buscarArt;
}
public void setBuscarArt(boolean buscarArt) {
	this.buscarArt = buscarArt;
}
public int getModoBusqProv() {
	return modoBusqProv;
}
public void setModoBusqProv(int modoBusqProv) {
	this.modoBusqProv = modoBusqProv;
}

public int getCantOCs() {
	return cantOCs;
}

public void setCantOCs(int cantOCs) {
	this.cantOCs = cantOCs;
}

public boolean isBuscarOC() {
	return buscarOC;
}

public void setBuscarOC(boolean buscarOC) {
	this.buscarOC = buscarOC;
}

public int getCantOCsConsult() {
	return cantOCsConsult;
}

public void setCantOCsConsult(int cantOCsConsult) {
	this.cantOCsConsult = cantOCsConsult;
}

public String getListadoOC() {
	return listadoOC;
}

public int getOcestado() {
	return ocestado;
}

public void setOcestado(int ocestado) {
	this.ocestado = ocestado;
}

public ArrayList<Proveedor> getProveedores() {
	return proveedores;
}

public void setProveedores(ArrayList<Proveedor> proveedores) {
	this.proveedores = proveedores;
}

public ArrayList<Articulo> getArticulos() {
	return articulos;
}

public void setArticulos(ArrayList<Articulo> articulos) {
	this.articulos = articulos;
}

public int getCantOSs() {
	return cantOSs;
}

public void setCantOSs(int cantOSs) {
	this.cantOSs = cantOSs;
}

public int getCantOSsConsult() {
	return cantOSsConsult;
}

public void setCantOSsConsult(int cantOSsConsult) {
	this.cantOSsConsult = cantOSsConsult;
}

public int getOsestado() {
	return osestado;
}

public void setOsestado(int osestado) {
	this.osestado = osestado;
}
public int getCantPecosas() {
	return cantPecosas;
}
public void setCantPecosas(int cantPecosas) {
	this.cantPecosas = cantPecosas;
}
public int getCantPecosasConsult() {
	return cantPecosasConsult;
}
public void setCantPecosasConsult(int cantPecosasConsult) {
	this.cantPecosasConsult = cantPecosasConsult;
}
public int getPecestado() {
	return pecestado;
}
public void setPecestado(int pecestado) {
	this.pecestado = pecestado;
}
}
