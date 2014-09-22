package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Marca;

public interface MarcaDAO {

	public List<String> getAll();
	public List<Marca> findByMarca(String descripcion);	
}
