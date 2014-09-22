package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Cotizacionproveedor;

public interface CotizacionproveedorDAO{
	public boolean save(Cotizacionproveedor cotizacionproveedor);
	public boolean delete();
	public boolean update();
	public List<Cotizacionproveedor> getAll();
	public Cotizacionproveedor findByObject(Cotizacionproveedor cotizacionproveedor);
	public String getSiguienteId(char tipoorden);
}
