package pe.edu.unmsm.quipucamayoc.dao.inf;

import pe.edu.unmsm.quipucamayoc.model.UsuarioFinzyt;

public interface UsuarioFinzytDAO {
	public UsuarioFinzyt getUsuariobd(String usunom,String contrasenia);
	public UsuarioFinzyt getUsuarioProyectoRol(int proyecto,String Rol);
}
