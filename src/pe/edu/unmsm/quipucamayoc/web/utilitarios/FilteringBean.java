package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

import pe.edu.unmsm.quipucamayoc.model.Ordencompra;

public class FilteringBean {

	private String filterOcnro="";
	private ArrayList<SelectItem> filterOcnros=new ArrayList<SelectItem>();
	
	public boolean filterStates(Object current){
		Ordencompra orden=(Ordencompra)current;
		if(filterOcnro.length()==0){
			return true;
		}
		if(orden.getId().getOcnro().toLowerCase().startsWith(filterOcnro.toLowerCase())){
			return true;			
		}else{
			return false;
		}
	}

	public String getFilterOcnro() {
		return filterOcnro;
	}

	public void setFilterOcnro(String filterOcnro) {
		this.filterOcnro = filterOcnro;
	}

	public ArrayList<SelectItem> getFilterOcnros() {
		return filterOcnros;
	}

	public void setFilterOcnros(ArrayList<SelectItem> filterOcnros) {
		this.filterOcnros = filterOcnros;
	}
	
	
}
