package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.model.PecosaId;

public interface PecosaDAO {
	
public boolean save(Pecosa Pecosa);
public boolean update(Pecosa Pecosa);
public boolean delete(Pecosa Pecosa);
public List<Pecosa> getAll(int proyectoId);
public Pecosa findByPecosa(Pecosa Pecosa);
public Pecosa findById(PecosaId id);
public String obtenerUltimoNro(int proyectoId);
public String obtenerUltimoNroPreImpreso(int proyectoId);
public boolean updateCampsEnEditarPecosa(Pecosa pecosa);
}
