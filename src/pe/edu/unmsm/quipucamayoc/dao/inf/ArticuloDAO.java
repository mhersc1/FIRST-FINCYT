package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Articulo;

public interface ArticuloDAO {

	public List<Articulo> getAll();
	public List<Articulo> findByArticulo(String descripcion,String tipo);
	
}
