package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;

public interface OrdencompraDAO {
	
public boolean save(Ordencompra ordencompra);
public boolean update(Ordencompra ordencompra);
public boolean updateCampsFyG(Ordencompra ordencompra);
public boolean updateFinal(Ordencompra ordencompra);
public boolean delete(Ordencompra ordencompra);
public List<Ordencompra> getAll(int proyectoId);
public Ordencompra findByOrdencompra(Ordencompra ordencompra);
public Ordencompra findById(OrdencompraId id);
public String obtenerUltimoNro(int proyectoId);
public String obtenerUltimoNroPreImpreso(int proyectoId);
public boolean updateCampsEnEditarOC(Ordencompra ordencompra);
public boolean updateCampsEnEditarPecosa(Ordencompra orden);
}
