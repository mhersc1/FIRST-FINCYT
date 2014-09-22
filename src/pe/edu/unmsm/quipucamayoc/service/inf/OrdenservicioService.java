package pe.edu.unmsm.quipucamayoc.service.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.model.OrdenservicioId;
import pe.edu.unmsm.quipucamayoc.model.Detalleos;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;

public interface OrdenservicioService {
	//public List<Ordencompra> listarOrdenesCompra();
	//public boolean save(Cotizacionproveedor cotprov,List<Detalleoc> detalles,char estado);
	public boolean save(Ordenservicio ordenservicio,List<Detalleos> detalles,char estado);
	public boolean update(Ordenservicio ordenservicio);
	public boolean updateOperacionesEditarOS(Ordenservicio ordenservicio,List<Detalleos> detalles);
	
	public boolean delete(Ordenservicio ordenservicio);
	public boolean validar(Ordenservicio ordenservicio);
	public Ordenservicio findByObject(Ordenservicio ordenservicio);
	public Ordenservicio findById(OrdenservicioId id);
	
	public List<Ordenservicio> getAll(int proyectoId);
	
	public String obtenerNroSiguiente(int proyectoId);
	public Long obtenerUltimoNroPreImpreso();
	
	public List<Ordenservicio> findByLikeObject(Ordenservicio ordenservicio);
	
	public List<Proveedor> getAllProveedors();
	public List<Proveedor> findByProveedor(Proveedor proveedor,int modo);
	
	public List<Articulo> getAllArticulos();
	public List<Articulo> findByArticulo(Articulo articulo);
	
	public List<String> getAllMarcas();
	public List<String> getAllUnidades();	
}
