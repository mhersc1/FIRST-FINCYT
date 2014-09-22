package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.OrdencompraDAO;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;

import org.springframework.orm.hibernate4.*;
/**
 * Home object for domain model class Ordencompra.
 * @see .Ordencompra
 * @author Hibernate Tools
 */
@Transactional
@Repository("ordencompraDAO")
public class OrdencompraDAOImpl implements OrdencompraDAO {
	
	@Autowired
	private  SessionFactory sessionFactory;


	@Override
	public boolean save(Ordencompra ordencompra) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		try {

			session.getTransaction().begin();			
			session.save(ordencompra);			
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
		
	}
	public boolean update(Ordencompra ordencompra){		
		Session session=this.getSessionFactory().openSession();
		
		try {//Lo mas probable para que no no funcione el update, podría haber sido el mapeo de Ordencompra.hbm.xml el renombre de OCNRO
/*			String sql="UPDATE ORDENCOMPRA SET OCESTADO='2' WHERE OCNRO='"+ordencompra.getId().getOcnro()+"' AND PROYECTOID="+ordencompra.getId().getProyectoid();
			session.getTransaction().begin();			
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();*/
			session.getTransaction().begin();
			String hql="update Ordencompra set ocestado='2' where id.ocnro='"+ordencompra.getId().getOcnro()+"'"
					+" and id.proyectoid='"+ordencompra.getId().getProyectoid()+"'";						
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
	
	public boolean updateFinal(Ordencompra ordencompra){		
		Session session=this.getSessionFactory().openSession();
		
		try {//Lo mas probable para que no no funcione el update, podría haber sido el mapeo de Ordencompra.hbm.xml el renombre de OCNRO
			session.getTransaction().begin();
			String hql="update Ordencompra set ocestado='4' where id.ocnro='"+ordencompra.getId().getOcnro()+"'"
					+" and id.proyectoid='"+ordencompra.getId().getProyectoid()+"'";						
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
	
	@Override
	public boolean delete(Ordencompra ordencompra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String obtenerUltimoNro(int proyectoId) {
		/**
		 * @Format ocnro: 		2014000001
		 */
		Session session=this.getSessionFactory().openSession();
		Long resultado;
		String numCont="000001";
		try {
			String query="select max(cast(id.ocnro as long))+1 from Ordencompra where proyectoid = '"+proyectoId+"'";
			resultado=(Long) session.createQuery(query).uniqueResult();
			
			if(resultado!=null ){
				numCont=resultado.toString().substring(4);
			}
			return numCont;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}	
		
	}
	
	@Override
	public String obtenerUltimoNroPreImpreso(int proyectoId) {
		/**
		 * @Format Preimpreso:	238-2014-000001	
		 * @return Devuelve el correlativo **
		 */
		Session session=this.getSessionFactory().openSession();
		Long resultados;
		String numCont="000001";
		try {
			String query="select max(cast(substr(ocpreimpresoini,10) as long))+1 from Ordencompra where proyectoid = '"+proyectoId+"'";			
			resultados=(Long) session.createQuery(query).uniqueResult();
			if(resultados!=null)
			numCont=resultados.toString();			
			return numCont;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public List<Ordencompra> getAll(int proyectoId) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		String hql="from Ordencompra where id.proyectoid = '"+proyectoId+"'";
		
		try {
			List<Ordencompra> lista=session.createQuery(hql).list();
			return lista;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Ordencompra findById(OrdencompraId id) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		Ordencompra orden;
		try {//No usar load
			orden=(Ordencompra)session.get(Ordencompra.class,id);
/*			String hql="from Ordencompra where id.ocnro ="+id.getOcnro()+
					" and id.proyectoid = "+id.getProyectoid();
			orden=(Ordencompra) session.createQuery(hql).uniqueResult();*/
			return orden;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	

	@Override
	public Ordencompra findByOrdencompra(Ordencompra ordencompra) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean updateCampsFyG(Ordencompra ordencompra) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		try {//Lo mas probable para que no no funcione el update, podría haber sido el mapeo de Ordencompra.hbm.xml el renombre de OCNRO
/*			String sql="UPDATE ORDENCOMPRA SET OCESTADO='2' WHERE OCNRO='"+ordencompra.getId().getOcnro()+"' AND PROYECTOID="+ordencompra.getId().getProyectoid();
			session.getTransaction().begin();			
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();*/
			session.getTransaction().begin();
			String hql="update Ordencompra set factura='"+ordencompra.getFactura()+"', "
					+ " guiaDeRemision='"+ordencompra.getGuiaDeRemision()+"', "
					+"  ocestado= '"+ordencompra.getOcestado()+"'"
					+" where id.ocnro='"+ordencompra.getId().getOcnro()+"' "
					+"  and id.proyectoid='"+ordencompra.getId().getProyectoid()+"'";						
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
	@Override
	public boolean updateCampsEnEditarOC(Ordencompra ordencompra){
		
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
			String hql="update Ordencompra set observacion = '"+ordencompra.getObservacion()+"' , "
					+ " referencia = '"+ordencompra.getReferencia()+"' , "
					+ " totaloc = '"+ordencompra.getTotaloc()+"' , "
					+ " totaligv = '"+ordencompra.getTotaligv()+"'"
					+ " where id.ocnro = '"+ordencompra.getId().getOcnro()+"'"
					+ " and id.proyectoid = '"+ordencompra.getId().getProyectoid()+"'";
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
	
	@Override
	public boolean updateCampsEnEditarPecosa(Ordencompra orden) {
		// TODO Auto-generated method stub
		Session session= this.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			String hql=" update Ordencompra set referencia = '"+orden.getReferencia()+"' , "
					+  " factura = '"+orden.getFactura()+"' , "
					+ "  guiaDeRemision = '"+orden.getGuiaDeRemision()+"' where id.ocnro = '"+orden.getId().getOcnro()+"' "
					+ "  and id.proyectoid = '"+orden.getId().getProyectoid()+"'";
			
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
