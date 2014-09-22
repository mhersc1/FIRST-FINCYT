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

import pe.edu.unmsm.quipucamayoc.dao.inf.DetalleocDAO;
import pe.edu.unmsm.quipucamayoc.model.Detalleoc;

/**
 * Home object for domain model class Detalleoc.
 * @see .Detalleoc
 * @author Hibernate Tools
 */
@Transactional
@Repository("detalleocDAO")
public class DetalleocDAOImpl implements DetalleocDAO{

	private static final Log log = LogFactory.getLog(DetalleocDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory ;

	@Override
	public boolean save(Detalleoc detalleoc) {
		Session session=this.getSessionFactory().openSession();
				
		// TODO Auto-generated method stub
		try {
			session.getTransaction().begin();
			session.save(detalleoc);
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
	public boolean delete(Detalleoc detalleoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Detalleoc detalleoc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Detalleoc> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Detalleoc findByDetalleoc(Detalleoc detalleoc) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getSiguienteDetOcId(){
		log.debug("finding DetalleOc instance by example");
        try {
        	Session session=this.getSessionFactory().openSession();
            Long detocid= (Long) session.createQuery("select max(cast(id.detocid as long)) from Detalleoc").uniqueResult();
            
            if(detocid!=null){
            	return detocid+1;
            }
            return 1L;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
	}
	
	public boolean updateCampsEnEditarOC(Detalleoc detalleoc){
		
		/**
		 *	Los campos editables para la orden de compra son:
		 *	Observacion
		 *	Nº Hoja de Requerimiento
		 *	******* Detalleoc *******
		 *	Detalle del articulo relacionado con la OC
		 *	Precio unitario del articulo relacionado con la OC. 
		 */
		Session session=this.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			String hql="update Detalleoc set detalle = '"+detalleoc.getDetalle()+"' , "
					+ " impuesto = '"+detalleoc.getImpuesto()+"' , "
					+ " cantidad = '"+detalleoc.getCantidad() +"' , "
					+ " preciounit = '"+detalleoc.getPreciounit()+"' , igv = '"+detalleoc.getIgv()+"' , "
					+ " subtotal = '"+detalleoc.getSubtotal()+"' where id.detocid= '"+detalleoc.getId().getDetocid()+"'"
					+ " and id.proyectoid = '"+detalleoc.getId().getProyectoid()+"'";
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

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
