package pe.edu.unmsm.quipucamayoc.dao.impl.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.unmsm.quipucamayoc.dao.inf.UsuarioFinzytDAO;
import pe.edu.unmsm.quipucamayoc.model.UsuarioFinzyt;

/**
 * Home object for domain model class UsuarioFinzyt.
 * @see .UsuarioFinzyt
 * @author Hibernate Tools
 */
@Transactional
@Repository("usuarioFinzytDAO")
public class UsuarioFinzytDAOImpl implements UsuarioFinzytDAO,Serializable{

	private static final Log log = LogFactory.getLog(UsuarioFinzytDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public UsuarioFinzyt getUsuariobd(String usunom, String contrasenia) {
		// TODO Auto-generated method stub
		List<UsuarioFinzyt> users;
		String Query= "from UsuarioFinzyt where usunom ='"+usunom+"' and"
				+ " password ='"+contrasenia+"'";
		
		try {
			
			users= sessionFactory.openSession().createQuery(Query).list();
			
			return users.get(0);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			sessionFactory.openSession().close();
			e.printStackTrace();
			return null;
		}		
	}
	
	@Override
	public UsuarioFinzyt getUsuarioProyectoRol(int proyecto,String Rol){
		List<UsuarioFinzyt> users;
		String Query = "from UsuarioFinzyt where rol ='"+Rol+"' and proyectoid = '"+proyecto+"'";
		try {			
			users= sessionFactory.openSession().createQuery(Query).list();			
			return users.get(0);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		
	}

}
