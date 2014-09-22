package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.OrdenservicioDAO;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.model.OrdenservicioId;

/**
 * Home object for domain model class Ordenservicio.
 * @see .Ordenservicio
 * @author Hibernate Tools
 */
@Transactional
@Repository("ordenservicioDAO")
public class OrdenservicioDAOImpl implements OrdenservicioDAO {
	
	@Autowired
	private SessionFactory sessionFactory ;

	@Override
	public boolean save(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().openSession();
		try{
			session.getTransaction().begin();
			session.save(ordenservicio);
			session.getTransaction().commit();
			return true;
		}catch(HibernateException e){
			session.getTransaction().rollback();
			return false;
		}
	}

	@Override
	public boolean delete(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		Session session=this.getSessionFactory().openSession();
		
		try {//Lo mas probable para que no no funcione el update, podría haber sido el mapeo de Ordencompra.hbm.xml el renombre de OCNRO
/*			String sql="UPDATE ORDENCOMPRA SET OCESTADO='2' WHERE OCNRO='"+ordencompra.getId().getOcnro()+"' AND PROYECTOID="+ordencompra.getId().getProyectoid();
			session.getTransaction().begin();			
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();*/
			session.getTransaction().begin();
			String hql="update Ordenservicio set osestado='2' where id.osnro='"+ordenservicio.getId().getOsnro()+"'"
					+" and id.proyectoid='"+ordenservicio.getId().getProyectoid()+"'";						
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
	public List<Ordenservicio> getAll(int proyectoId) {
		// TODO Auto-generated method stub
		Session session = this.getSessionFactory().openSession();
		String hql = "from Ordenservicio where id.proyectoid = '"+proyectoId+"'";
		
		try{
			List<Ordenservicio> lista = session.createQuery(hql).list();
			return lista;
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Ordenservicio findByOrdenservicio(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Ordenservicio findById(OrdenservicioId id){
		Session session = this.getSessionFactory().openSession();
		Ordenservicio servicio;
		try{
			servicio = (Ordenservicio)session.load(Ordenservicio.class, id);
			return servicio;
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String obtenerUltimoNro(int proyectoId){
		/**
		 * @Format osnro: 		2014000001
		 */
		Session session = this.getSessionFactory().openSession();
		Long resultado;
		String numCont = "000001";
		try{
			String hql = "select max(cast(id.osnro as long))+1 from Ordenservicio where proyectoid = '"+proyectoId+"'";
			resultado = (Long)session.createQuery(hql).uniqueResult();
			
			if(resultado!=null){
				numCont = resultado.toString().substring(4);
			}
			return numCont;
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public String obtenerUltimoNroPreImpreso(int proyectoId){
		/**
		 * @Format Preimpreso:	238-2014-000001	
		 * @return Devuelve el correlativo **
		 */
		Session session = this.getSessionFactory().openSession();
		Long resultados;
		String numCont="000001";
		try {
			String hql="select max(cast(substr(ospreimpresoini,10) as long))+1 from Ordenservicio where proyectoid = '"+proyectoId+"'";			
			resultados=(Long) session.createQuery(hql).uniqueResult();
			if(resultados!=null)
			numCont=resultados.toString();			
			return numCont;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean updateCampsEnEditarOS(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		/**
		 *	Los campos editables para la orden de servicio son:
		 *	Observacion
		 *	Nº Hoja de Requerimiento
		 *	******* Detalleos *******
		 *	Detalle del servicio relacionado con la OS
		 *	Precio unitario del servicio relacionado con la OS. 
		 */		
		Session session=this.getSessionFactory().openSession();
		try {
			session.getTransaction().begin();
			String hql="update Ordenservicio set observacion = '"+ordenservicio.getObservacion()+"' , "
					 + " referencia = '"+ordenservicio.getReferencia()+"' , "
					 + " totalos = '"+ordenservicio.getTotalos()+"' , "
					 + " totaligv = '"+ordenservicio.getTotaligv()+"' "
					 + " where id.osnro = '"+ordenservicio.getId().getOsnro()+"' and "
					 + " id.proyectoid = '"+ordenservicio.getId().getProyectoid()+"'";
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	


}
