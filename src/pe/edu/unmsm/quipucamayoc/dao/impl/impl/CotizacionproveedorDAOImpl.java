package pe.edu.unmsm.quipucamayoc.dao.impl.impl;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.CotizacionproveedorDAO;
import pe.edu.unmsm.quipucamayoc.model.Cotizacionproveedor;

/**
 * Home object for domain model class Cotizacionproveedor.
 * @see .Cotizacionproveedor
 * @author Hibernate Tools
 */
@Transactional
@Repository("cotizacionproveedorDAO")
public class CotizacionproveedorDAOImpl implements CotizacionproveedorDAO {

	private static final Log log = LogFactory
			.getLog(CotizacionproveedorDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean save(Cotizacionproveedor cotizacionproveedor) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			session.save(cotizacionproveedor);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;			
		}finally{
			session.close();
		}
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cotizacionproveedor> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cotizacionproveedor findByObject(
			Cotizacionproveedor cotizacionproveedor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSiguienteId(char tipoorden) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
        try {
            List results = session.createQuery("select max(cast(model.id.cotprovid as long)) from Cotizacionproveedor as model " +
            		" where model.id.tipoorden = '"+tipoorden+"'").list();
            if(results.size() > 0 && results.get(0) != null){
            	System.out.println("size " + results.size());
            	return "" + (Long.parseLong(results.get(0).toString()) + 1);
            }
            return "1";
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
