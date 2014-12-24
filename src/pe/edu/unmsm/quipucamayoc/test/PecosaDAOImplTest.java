package pe.edu.unmsm.quipucamayoc.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import pe.edu.unmsm.quipucamayoc.dao.impl.impl.PecosaDAOImpl;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HibernateUtil;

public class PecosaDAOImplTest {
	private static final Log log=LogFactory.getLog(PecosaDAOImplTest.class);

	//@Test
	public void obtenerUltimoNro() {
		int proyectoId=1;
		PecosaDAOImpl ordenDAO=new PecosaDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNro(proyectoId));
	}
	
	//@Test
	public void obtenerUltimoNroPreImpresoIni() {
		int proyectoId=1;
		PecosaDAOImpl ordenDAO=new PecosaDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNroPreImpreso(proyectoId));
	
	
	}
	
	@Test
	public void getAll() {
		int proyectoId=1;
		List<Pecosa> pecosas=new ArrayList<Pecosa>();
		PecosaDAOImpl ordenDAO=new PecosaDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());		
		ordenDAO.getAll(proyectoId);
		for(Pecosa orden:pecosas){
			log.info("Numero ***");
			System.out.println("Orden N°" + orden.getId().getPecnro() + orden.getId().getProyectoid());			
		}
	}

}
