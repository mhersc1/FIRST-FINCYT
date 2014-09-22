package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.PecosaDAO;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.model.PecosaId;
import org.springframework.orm.hibernate4.*;
/**
 * Home object for domain model class Pecosa.
 * @see .Pecosa
 * @author Hibernate Tools
 */
@Transactional
@Repository("pecosaDAO")
public class PecosaDAOImpl implements PecosaDAO {
	
	@Autowired
	private  SessionFactory sessionFactory;


	@Override
	public boolean save(Pecosa pecosa) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		try {

			session.getTransaction().begin();			
			session.save(pecosa);			
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
			return false;
		}
		
	}
	public boolean update(Pecosa pecosa){		
		Session session=this.getSessionFactory().openSession();
		
		try {
			session.getTransaction().begin();
			String hql="update Pecosa set pecestado='2' where id.pecnro='"+pecosa.getId().getPecnro()+"'"
					+" and id.proyectoid='"+pecosa.getId().getProyectoid()+"'";						
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
	public boolean delete(Pecosa pecosa) {
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
			String query="select max(id.pecnro)+1 from Pecosa where proyectoid = '"+proyectoId+"'";
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
			String query="select max(cast(substr(preimpresoinicial,10) as long))+1 from Pecosa where proyectoid = '"+proyectoId+"'";			
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
	public List<Pecosa> getAll(int proyectoId) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		String hql="from Pecosa where id.proyectoid = '"+proyectoId+"'";
		
		try {
			List<Pecosa> lista=session.createQuery(hql).list();
			return lista;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Pecosa findById(PecosaId id) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		Pecosa orden;
		try {//No usar load
			orden=(Pecosa)session.get(Pecosa.class,id);
/*			String hql="from Pecosa where id.ocnro ="+id.getOcnro()+
					" and id.proyectoid = "+id.getProyectoid();
			orden=(Pecosa) session.createQuery(hql).uniqueResult();*/
			return orden;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Pecosa findByPecosa(Pecosa ordencompra) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean updateCampsEnEditarPecosa(Pecosa pecosa) {
		/**
		 * El nº de expediente o documento de referencia 
		 * no se actualizara en la pecosa.
		 */
		Session session=this.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			String hql="update Pecosa set pecobs = '"+pecosa.getPecobs()+"' , "
					+  "pecdes = '"+pecosa.getPecdes()+"' , pecdesdir = '"+pecosa.getPecdesdir()+"'"
					+  "where id.pecnro ='"+pecosa.getId().getPecnro()+"' and id.proyectoid = '"+pecosa.getId().getProyectoid()+"' ";
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
