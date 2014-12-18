package pe.edu.unmsm.quipucamayoc.test;

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
	}
	

}
