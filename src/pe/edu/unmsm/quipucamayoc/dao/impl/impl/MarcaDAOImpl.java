package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.MarcaDAO;
import pe.edu.unmsm.quipucamayoc.model.Marca;

/**
 * Home object for domain model class Detalleoc.
 * @see .Detalleoc
 * @author Hibernate Tools
 */
@Transactional
@Repository("marcaDAO")
public class MarcaDAOImpl implements MarcaDAO{

	private static final Log log = LogFactory.getLog(MarcaDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory ;

	@Override
	public List<String> getAll() {
		// TODO Auto-generated method stub
		try {
			String hql="select mardes from Marca order by mardes  asc ";
			return (List<String>) sessionFactory.openSession().createQuery(hql).list();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 throw e;
		}
		
	}

	@Override
	public List<Marca> findByMarca(String descripcion) {
		// TODO Auto-generated method stub
		try {
			String hql="from Marca where mardes = '"+descripcion+"'";
			return (List<Marca>) sessionFactory.openSession().createQuery(hql).list();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
}
