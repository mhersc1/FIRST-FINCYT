package pe.edu.unmsm.quipucamayoc.service.impl;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.quipucamayoc.dao.inf.UsuarioFinzytDAO;
import pe.edu.unmsm.quipucamayoc.model.UsuarioFinzyt;
import pe.edu.unmsm.quipucamayoc.service.inf.UsuarioFinzytService;
/**
 * 
 * @author Mher
 * Service para control de Usuario
 */


@Service("usuarioFinzytService")
public class UsuarioFinzytServiceImpl implements UsuarioFinzytService{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UsuarioFinzytDAO usuarioDAO;
	
	
	private Log log=LogFactory.getLog(UsuarioFinzytServiceImpl.class);
	@Override
	public Authentication login(String user,String password,String rememberMe) {
		// TODO Auto-generated method stub
		logout();	

			try {
				HttpServletRequest request = getRequest();
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user, password);
				authReq.setDetails(new WebAuthenticationDetails(request));				
				Authentication auth = authenticationManager.authenticate(authReq);
				
		    	SecurityContextHolder.getContext().setAuthentication(auth);    	
		    	
				return auth;
			} catch (AuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}    	
	}

	@Override
	public String logout() {
		// TODO Auto-generated method stub
		SecurityContextHolder.clearContext();
		log.info("logout");
		return "loggedout";
	}

	@Override
	public UsuarioFinzyt getUsuario(String usuario, String password) {
		// TODO Auto-generated method stub
		return this.usuarioDAO.getUsuariobd(usuario, password);
	}
	@Override
	public UsuarioFinzyt getUsuarioProyectoRol(int proyecto,String rol){
		// TODO Auto-generated method stub
		return this.usuarioDAO.getUsuarioProyectoRol(proyecto, rol);
	}
	
	protected HttpServletRequest getRequest() {
		ExternalContext context = 
			FacesContext.getCurrentInstance().getExternalContext();
	    HttpServletRequest request = 
	    	(HttpServletRequest) context.getRequest();	    
	    return request;
	}
}
