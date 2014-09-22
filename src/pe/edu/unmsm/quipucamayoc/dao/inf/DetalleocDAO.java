package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Detalleoc;

public interface DetalleocDAO {

	public boolean save(Detalleoc detalleoc);
	public boolean delete(Detalleoc detalleoc);
	public boolean update(Detalleoc detalleoc);
	public List<Detalleoc> getAll();
	public Detalleoc findByDetalleoc(Detalleoc detalleoc);
	public Long getSiguienteDetOcId();
	public boolean updateCampsEnEditarOC(Detalleoc detalleoc);
}
