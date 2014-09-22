package pe.edu.unmsm.quipucamayoc.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;

import pe.edu.unmsm.quipucamayoc.model.UsuarioFinzyt;
import pe.edu.unmsm.quipucamayoc.service.inf.OrdencompraService;
import pe.edu.unmsm.quipucamayoc.service.inf.UsuarioFinzytService;
@Controller
@Scope("session")
public class UsuarioFinzytMB implements Serializable {
	@Autowired
	private UsuarioFinzytService usuarioFinzytService;
	@Autowired
	private OrdencompraService ordencompraService;
	@Autowired

	private AuthenticationManager authenticationManager; 
	private boolean menuOperaciones;
	private boolean menuConsultas;
	private String user			;
	private String password		;
	private String rememberMe	;
	private int proyecto			;
	private static String ROL_CAP="CAP";
	private static String ROL_CGI="CGI";
	private List<GrantedAuthority> roles;
	private  String rol;
	private String email;
	private UsuarioFinzyt destino;
	private Application app;
	private String errorLogueo="";
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	private Log log=LogFactory.getLog(UsuarioFinzytMB.class);
	public UsuarioFinzyt getUsuarioProyectoRol(int proyecto, String rol){
		this.destino = usuarioFinzytService.getUsuarioProyectoRol(proyecto, rol);
		return destino;
	}
	
	private FacesMessage facesMessage;	
	public UsuarioFinzytMB() {
		// TODO Auto-generated constructor stub
		super();
	}
	public boolean escogerperfil(){
		boolean perfilConocido=true;	
		UsuarioFinzyt usuario = usuarioFinzytService.getUsuario(user, password);
		proyecto=usuario.getProyectoid();
		
		setEmail(usuario.getEmail());
		
		//La parte de Authenticacion del usuario.
		for(int i=0;i<roles.size();i++){
			setRol(roles.get(i).getAuthority());
			if(getRol().equals(ROL_CAP)){
				setMenuOperaciones(true);
				setMenuConsultas(true);
				setProyecto(proyecto);
			}else if(getRol().equals(ROL_CGI)){
				setMenuOperaciones(false);
				setMenuConsultas(true);
				setProyecto(proyecto);
			}else{
			perfilConocido=false;
			}
		}
		return perfilConocido;
	}

	protected HttpServletRequest getRequest() {
		ExternalContext context = 
			FacesContext.getCurrentInstance().getExternalContext();
	    HttpServletRequest request = 
	    	(HttpServletRequest) context.getRequest();	    
	    return request;
	}
	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
	

	public String login() {

			logout();
			Authentication result = usuarioFinzytService.login(user, password,
					"1");			
			if(result !=null && result.isAuthenticated()){
				roles = new ArrayList<GrantedAuthority>(
						result.getAuthorities());
				if(escogerperfil()){
					log.info("Usuario Control : "+this);
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(	FacesMessage.SEVERITY_INFO,"Bienvenido!!",""));			        
					return "welcome";
				}else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(	FacesMessage.SEVERITY_INFO,"WTF? Usuario no válido",""));
					errorLogueo="Usuario no v\u00E1lido.";
					return "login";
				}			
				
			}else{
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(	FacesMessage.SEVERITY_INFO,"Usuario no válido?",""));
					errorLogueo="Usuario no v\u00E1lido.";
					return "login";					
			}				
	}
	
	public String logout() {
		
/*        try {
        	String rutaRaiz;
        	HttpServletRequest request= (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        	rutaRaiz=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(rutaRaiz+"/j_spring_security_logout");		
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		errorLogueo="";
		return usuarioFinzytService.logout();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public boolean isMenuOperaciones() {
		return menuOperaciones;
	}

	public void setMenuOperaciones(boolean menuOperaciones) {
		this.menuOperaciones = menuOperaciones;
	}

	public boolean isMenuConsultas() {
		return menuConsultas;
	}

	public void setMenuConsultas(boolean menuConsultas) {
		this.menuConsultas = menuConsultas;
	}

	public int getProyecto() {
		return proyecto;
	}

	public void setProyecto(int proyecto) {
		this.proyecto = proyecto;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	public String getErrorLogueo() {
		return errorLogueo;
	}
	public void setErrorLogueo(String errorLogueo) {
		this.errorLogueo = errorLogueo;
	}

}
