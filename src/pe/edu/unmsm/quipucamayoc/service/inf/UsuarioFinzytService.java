package pe.edu.unmsm.quipucamayoc.service.inf;

import org.springframework.security.core.Authentication;

import pe.edu.unmsm.quipucamayoc.model.UsuarioFinzyt;

public interface UsuarioFinzytService {
public Authentication login(String user,String password,String rememberMe);
public String logout();
public UsuarioFinzyt getUsuario(String usuario,String password);
public UsuarioFinzyt getUsuarioProyectoRol(int proyecto,String rol);
}
