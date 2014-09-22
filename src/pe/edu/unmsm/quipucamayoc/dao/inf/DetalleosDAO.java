package pe.edu.unmsm.quipucamayoc.dao.inf;

import java.util.List;

import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.Detalleos;

public interface DetalleosDAO {

	public boolean save(Detalleos detalleos);
	public boolean delete(Detalleos detalleos);
	public boolean update(Detalleos detalleos);
	public List<Detalleos> getAll();
	public Detalleos findByDetalleos(Detalleos detalleos);
	public Long getSiguienteDetOsId();
	public boolean updateCampsEnEditarOS(Detalleos detalleos);
}
