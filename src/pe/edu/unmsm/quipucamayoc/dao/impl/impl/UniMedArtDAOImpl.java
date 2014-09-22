package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.UniMedArtDAO;
import pe.edu.unmsm.quipucamayoc.model.Marca;
import pe.edu.unmsm.quipucamayoc.model.UniMedArt;

/**
 * Home object for domain model class Detalleoc.
 * @see .Detalleoc
 * @author Hibernate Tools
 */
@Transactional
@Repository("uniMedArtDAO")
public class UniMedArtDAOImpl implements UniMedArtDAO{
	@Autowired
	private SessionFactory sessionFactory ;
	@Override
	public List<String> getAll() {
		// TODO Auto-generated method stub
		try {
			String hql="select unimedabr from UniMedArt where unimedest like 'A' order by unimedabr";
			return (List<String>) sessionFactory.openSession().createQuery(hql).list();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 throw e;
		}
	}

	@Override
	public List<UniMedArt> findByUnidad(String descripcion) {
		// TODO Auto-generated method stub
		try{
			String hql="from UniMedArt where unimedabr = +'"+descripcion+"'";
			return (List<UniMedArt>)sessionFactory.openSession().createQuery(hql).list();
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
			
		}
	}

}
