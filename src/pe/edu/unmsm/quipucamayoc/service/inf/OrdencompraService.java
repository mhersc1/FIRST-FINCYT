package pe.edu.unmsm.quipucamayoc.service.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;

public interface OrdencompraService {

	//public List<Ordencompra> listarOrdenesCompra();
	//public boolean save(Cotizacionproveedor cotprov,List<Detalleoc> detalles,char estado);
	public boolean save(Ordencompra ordencompra,List<Detalleoc> detalles);
	public boolean validar(Ordencompra ordencompra);
	public boolean updateOperacionesEditarOC(Ordencompra ordencompra,List<Detalleoc> detalles);
	
	public boolean delete(Ordencompra ordencompra);
			
	public Ordencompra findByObject(Ordencompra ordencompra);
	public Ordencompra findById(OrdencompraId id);
	
	public List<Ordencompra> getAll(int proyectoId);
	
	public String obtenerNroSiguiente(int proyectoId);
	public Long obtenerUltimoNroPreImpreso();
	
	public List<Ordencompra> findByLikeObject(Ordencompra ordencompra);
	
	public List<Proveedor> getAllProveedors();
	public List<Proveedor> findByProveedor(Proveedor proveedor,int modo);
	
	public List<Articulo> getAllArticulos();
	public List<Articulo> findByArticulo(Articulo articulo);
	
	public List<String> getAllMarcas();
	public List<String> getAllUnidades();
	
	
	
}
