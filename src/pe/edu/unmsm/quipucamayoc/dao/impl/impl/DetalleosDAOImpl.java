package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.DetalleosDAO;
import pe.edu.unmsm.quipucamayoc.model.Detalleos;

/**
 * Home object for domain model class Detalleos.
 * @see .Detalleos
 * @author Hibernate Tools
 */
@Transactional
@Repository("detalleosDAO")
public class DetalleosDAOImpl implements DetalleosDAO {

	private static final Log log = LogFactory.getLog(DetalleosDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory ;

	@Override
	public boolean save(Detalleos detalleos) {
		Session session=this.getSessionFactory().openSession();
		
		// TODO Auto-generated method stub
		try {
			session.getTransaction().begin();
			session.save(detalleos);
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}

	@Override
	public boolean delete(Detalleos detalleos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Detalleos detalleos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Detalleos> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Detalleos findByDetalleos(Detalleos detalleos) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getSiguienteDetOsId(){
		log.debug("finding DetalleOs instance by example");
        try {
        	Session session=this.getSessionFactory().openSession();
            Long detosid= (Long) session.createQuery("select max(cast(id.detosid as long)) from Detalleos").uniqueResult();
            
            if(detosid!=null){
            	return detosid+1;
            }
            return 1L;
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

	@Override
	public boolean updateCampsEnEditarOS(Detalleos detalleos) {
		// TODO Auto-generated method stub
		/**
		 *	Los campos editables para la orden de servicio son:
		 *	Observacion
		 *	Nº Hoja de Requerimiento
		 *	******* Detalleos *******
		 *	Detalle del articulo relacionado con la OS
		 *	Precio unitario del articulo relacionado con la OS. 
		 */
		Session session=this.getSessionFactory().openSession();
		
		try {			
			session.getTransaction().begin();
			String hql="update Detalleos set detalle = '"+detalleos.getDetalle()+"' , "
					+ " impuesto = '"+detalleos.getImpuesto()+"' , "				
					+ " preciounit = '"+detalleos.getPreciounit()+"' , igv = '"+detalleos.getIgv()+"' , "
					+ " subtotal = '"+detalleos.getSubtotal()+"' where id.detosid= '"+detalleos.getId().getDetosid()+"'"
					+ " and id.proyectoid = '"+detalleos.getId().getProyectoid()+"'";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
	}
}
