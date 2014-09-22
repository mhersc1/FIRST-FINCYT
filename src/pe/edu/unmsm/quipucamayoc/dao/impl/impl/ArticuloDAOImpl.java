package pe.edu.unmsm.quipucamayoc.dao.impl.impl;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.ArticuloDAO;
import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;

/**
 * Home object for domain model class Articulo.
 * @see .Articulo
 * @author Hibernate Tools
 */
@Transactional
@Repository("articuloDAO")
public class ArticuloDAOImpl implements ArticuloDAO{

	private static final Log log = LogFactory.getLog(ArticuloDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory ;

	@Override
	public List<Articulo> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Articulo> findByArticulo(String descripcion,String tipo) {
		// TODO Auto-generated method stub
		   try {
			   System.out.print(descripcion);
		         String queryString = "from Articulo where artdes" 
		         						+ " like '%"+descripcion+"%' and arttip = '"+tipo+"'";
				 return (List<Articulo>) sessionFactory.openSession().createQuery(queryString).list();				 
		      } catch (RuntimeException re) {
		         log.error("find by property name failed", re);
		         throw re;
		      }
	}




	
}
