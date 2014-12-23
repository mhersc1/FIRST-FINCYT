package pe.edu.unmsm.quipucamayoc.test;

<<<<<<< HEAD
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import pe.edu.unmsm.quipucamayoc.dao.impl.impl.OrdencompraDAOImpl;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HibernateUtil;

public class OrdencompraDAOImplTest {
	private static final Log log=LogFactory.getLog(OrdencompraDAOImplTest.class);
	@Test
	public void obtenerUltimoNro() {
		int proyectoId=1;
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNro(proyectoId));
	}

	@Test
	public void obtenerUltimoNroPreImpresoIni() {
		int proyectoId=1;
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNroPreImpreso(proyectoId));
=======
import org.junit.Test;

import pe.edu.unmsm.quipucamayoc.dao.impl.impl.OrdencompraDAOImpl;
import pe.edu.unmsm.quipucamayoc.dao.inf.OrdencompraDAO;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HibernateUtil;

public class OrdencompraDAOImplTest {

	@Test
	public void obtenerUltimoNro() {
		int proyectoId=1;
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		ordenDAO.obtenerUltimoNro(proyectoId);
	}

	//@Test
	public void obtenerUltimoNroPreImpresoIni() {
		int proyectoId=1;
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		ordenDAO.obtenerUltimoNroPreImpreso(proyectoId);
>>>>>>> refs/remotes/origin/master
	}
	

}
