package pe.edu.unmsm.quipucamayoc.test;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import pe.edu.unmsm.quipucamayoc.dao.impl.impl.OrdencompraDAOImpl;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HibernateUtil;

public class OrdencompraDAOImplTest {
	private static final Log log=LogFactory.getLog(OrdencompraDAOImplTest.class);
	//@Test
	public void obtenerUltimoNro() {
		int proyectoId=1;
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNro(proyectoId));
	}

	//@Test
	public void obtenerUltimoNroPreImpresoIni() {
		int proyectoId=1;
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());
		log.info("Procesando ***");
		log.info("Soy el numero "+ ordenDAO.obtenerUltimoNroPreImpreso(proyectoId));


	}
	
	@Test
	public void getAll() {
		int proyectoId=1;
		List<Ordencompra> ordenes=new ArrayList<Ordencompra>();
		OrdencompraDAOImpl ordenDAO=new OrdencompraDAOImpl();
		ordenDAO.setSessionFactory(HibernateUtil.getSessionFactory());		
		ordenDAO.getAll(proyectoId);
		for(Ordencompra orden:ordenes){
			log.info("Numero ***");
			System.out.println("Orden N�" + orden.getId().getOcnro() + orden.getId().getProyectoid());			
		}
	}
	}
