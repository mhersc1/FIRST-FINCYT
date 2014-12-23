package pe.edu.unmsm.quipucamayoc.web.utilitarios;

import java.io.InputStream;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	  private static final SessionFactory sessionFactory;
	  private static Log log=LogFactory.getLog(HibernateUtil.class);
	    static {
	    	Properties props;
	        try {
	        	props=getProperties();
	            //Programmatic Configuration
	        	Configuration cfg = new Configuration()
	        	//Configuration Mapping Files        	
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Articulo.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Cotizacionproveedor.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Detalleoc.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Detalleos.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Marca.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Ordencompra.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Ordenservicio.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Pecosa.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/Proveedor.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/UniMedArt.hbm.xml")
	        	.addResource("pe/edu/unmsm/quipucamayoc/dao/impl/hbm/UsuarioFinzyt.hbm.xml")
	        	//Configuration Hibernate Properties
	        	.addProperties(props);
	        	sessionFactory =cfg.buildSessionFactory();
	        } catch (Throwable ex) {
	            // Log the exception. 
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        } finally {
			}
	    }
	    private static Properties getProperties(){
	    	/**
	    	 * Desarrollo
	    	 */
	    	Properties props=getProperties("config.properties");
	    	/**
	    	 * Produccion
	    	 *   
	    	String host=System.getenv("OPENSHIFT_MYSQL_DB_HOST");
	    	String port=System.getenv("OPENSHIFT_MYSQL_DB_PORT");    	
	    	String username=System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
	    	String password=System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
	    	String url="jdbc:mysql://"+host+":"+port+"/";
	    	props.setProperty("hibernate.connection.url", url);
	    	props.setProperty("hibernate.connection.username", username);
	    	props.setProperty("hibernate.connection.password", password);
	    	 */ 
	    	return props;
	    }
	    
	  	private static Properties getProperties(String file){
			Properties props=new Properties();
			InputStream input=null;
	  		try {			
				input=FacesContext.class.getClassLoader().getResourceAsStream(file);			
				props.load(input);				
				return props;							
			} catch (Exception e) {
				// TODO Auto-generated catch block			
				log.info("Ocurrio excepcion al leer el Properties :"+ file);
				e.printStackTrace();
				return null;
			}finally{
				if(input==null){
					System.out.println("No se pudo encontrar "+ file);
					
				}
			}
		}	    
	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }
}
