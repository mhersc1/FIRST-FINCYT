package pe.edu.unmsm.quipucamayoc.dao.impl.impl;
// default package
// Generated 07/06/2014 03:48:28 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.ProveedorDAO;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;

/**
 * Home object for domain model class Proveedor.
 * @see .Proveedor
 * @author Hibernate Tools
 */
@Transactional
@Repository("proveedorDAO")
public class ProveedorDAOImpl implements ProveedorDAO {
	
	private static final Log log = LogFactory.getLog(ProveedorDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Proveedor> getAll() {
		// TODO Auto-generated method stub
		try {
			String query="from Proveedor";		
			return (List<Proveedor>) sessionFactory.openSession().createQuery(query);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			log.error("find by property name failed", e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Proveedor findByObject(Proveedor proveedor) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public List<Proveedor> findByRUC(String ruc) {
		// TODO Auto-generated method stub
	   try {
		         String queryString = "from Proveedor where proruc " 
		         						+ " = '"+ruc+"'";
				 return (List<Proveedor>) sessionFactory.openSession().createQuery(queryString).list();				 
		      } catch (RuntimeException re) {
		         log.error("find by property name failed", re);
		         throw re;
		      }
	}

	@Override
	public List<Proveedor> findByRazSoc(String razsoc) {
		// TODO Auto-generated method stub
        try {
        	razsoc=razsoc.toUpperCase();
			String queryString = "from Proveedor where prorazsoc" 
								+ " like '%"+razsoc+"%'";
			return (List<Proveedor>) sessionFactory.openSession().createQuery(queryString).list();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
}
