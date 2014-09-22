package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.UniMedArt;

public interface UniMedArtDAO {

	public List<String> getAll();
	public List<UniMedArt> findByUnidad(String descripcion);	
}
