package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Proveedor;

public interface ProveedorDAO {
	public List<Proveedor> getAll();
	public Proveedor findByObject(Proveedor proveedor);
	public List<Proveedor> findByRUC(String ruc);
	public List<Proveedor> findByRazSoc(String razsoc);
}
