package pe.edu.unmsm.quipucamayoc.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import pe.edu.unmsm.quipucamayoc.dao.impl.impl.OrdenservicioDAOImpl;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HibernateUtil;

public class OrdenservicioDAOImplTest {
	private static final Log log=LogFactory.getLog(OrdenservicioDAOImplTest.class);
	//@Test
	public void obtenerUltimoNro() {
		int proyectoId=1;
		OrdenservicioDAOImpl ordenDAO=new OrdenservicioDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNro(proyectoId));
	}

	//@Test
	public void obtenerUltimoNroPreImpresoIni() {
		int proyectoId=1;
		OrdenservicioDAOImpl ordenDAO=new OrdenservicioDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNroPreImpreso(proyectoId));


	}
	
	@Test
	public void getAll() {
		int proyectoId=1;
		List<Ordenservicio> ordenes=new ArrayList<Ordenservicio>();
		OrdenservicioDAOImpl ordenDAO=new OrdenservicioDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());		
		ordenDAO.getAll(proyectoId);
		for(Ordenservicio orden:ordenes){
			log.info("Numero ***");
			System.out.println("Orden N°" + orden.getId().getOsnro() + orden.getId().getProyectoid());			
		}
	}
}
