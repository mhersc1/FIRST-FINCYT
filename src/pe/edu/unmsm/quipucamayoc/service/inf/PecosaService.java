package pe.edu.unmsm.quipucamayoc.service.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.model.PecosaId;

public interface PecosaService {

	//public List<Pecosa> listarOrdenesCompra();
	//public boolean save(Cotizacionproveedor cotprov,List<Detalleoc> detalles,char estado);
	public boolean save(Pecosa pecosa,Ordencompra orden);
	public boolean validar(Pecosa pecosa, Ordencompra orden);
	public boolean update(Pecosa pecosa);
	
	public boolean delete(Pecosa pecosa);
			
	public Pecosa findByObject(Pecosa pecosa);
	public Pecosa findById(PecosaId id);
	
	public List<Pecosa> getAll(int proyectoId);
	
	public String obtenerNroSiguiente(int proyectoId);
	public Long obtenerUltimoNroPreImpreso();
	
	public List<Pecosa> findByLikeObject(Pecosa pecosa);
	public boolean updateOperacionesEditarPecosa(Pecosa pecosa,Ordencompra orden);
	
}
