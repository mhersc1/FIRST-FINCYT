package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.model.OrdenservicioId;

public interface OrdenservicioDAO {
	
	public boolean save(Ordenservicio ordenservicio);
	public boolean delete(Ordenservicio ordenservicio);
	public boolean update(Ordenservicio ordenservicio);
	public List<Ordenservicio> getAll(int proyectoId);
	public Ordenservicio findByOrdenservicio(Ordenservicio ordenservicio);
	public Ordenservicio findById(OrdenservicioId id);
	public String obtenerUltimoNro(int proyectoId);
	public String obtenerUltimoNroPreImpreso(int proyectoId);
	public boolean updateCampsEnEditarOS(Ordenservicio ordenservicio);
	
}
