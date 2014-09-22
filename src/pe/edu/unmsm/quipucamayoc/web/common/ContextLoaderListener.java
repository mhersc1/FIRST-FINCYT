package pe.edu.unmsm.quipucamayoc.web.common;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.xml.internal.ws.util.StringUtils;

public class ContextLoaderListener extends org.springframework.web.context.ContextLoaderListener {
	public static String DB;
	public static String PATH;
    private static ApplicationContext context;
	
	public static Object getBean(String name){
		return context.getBean(name);
	}
	
/*	@SuppressWarnings("unchecked")
	public static Object getBean(Class name){
		Object bean = null;
		String beanName = ClassUtils.getShortName(name);
		if(!StringUtils.isEmpty(beanName) && context != null){
			bean = context.getBean(beanName);
		}		
		return bean;
	}*/

	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		context = null;
	}

	public void contextInitialized(ServletContextEvent event) {
   	  super.contextInitialized(event);
	  context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	  System.out.print("Contexto: "+context.getId());
	  PATH = event.getServletContext().getRealPath("/");
	  DB = "db";
	}
	
	
	/*
	private Object getBean(FacesContext currentInstance,String name){
		return currentInstance.getApplication().getVariableResolver().resolveVariable(currentInstance, name);
	}*/
/*
	private FacesContext getFacesContext(final ServletRequest request, final ServletResponse response) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
		return facesContext;
		}

		// Si el facesContext es null se crea un Contexto por default

		FacesContextFactory contextFactory = (FacesContextFactory)FactoryFinder.getFactory(FactoryFinder. FACES_CONTEXT_FACTORY);

		LifecycleFactory lifecycleFactory = (LifecycleFactory)FactoryFinder.getFactory(FactoryFinder. LIFECYCLE_FACTORY);

		Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory. DEFAULT_LIFECYCLE);
		facesContext = contextFactory.getFacesContext(this.config.getServletContext(), request, response, lifecycle);

		return facesContext;
	}*/
	
	

}
