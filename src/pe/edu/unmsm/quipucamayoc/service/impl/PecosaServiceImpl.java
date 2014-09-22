package pe.edu.unmsm.quipucamayoc.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.quipucamayoc.dao.inf.OrdencompraDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.PecosaDAO;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.model.PecosaId;
import pe.edu.unmsm.quipucamayoc.service.inf.PecosaService;
/**
 * 
 * @author Mher
 * Service para las Ordenes de compra
 */

@Service("pecosaService")
public class PecosaServiceImpl implements PecosaService{
	
	@Autowired
	private PecosaDAO pecosaDAO;
	@Autowired
	private OrdencompraDAO ordencompraDAO;
	
	private Log log=LogFactory.getLog(OrdencompraDAO.class);
	
	public boolean save(Pecosa pecosa,Ordencompra orden) {
		// TODO Auto-generated method stub		
		PecosaId pecId=null;
		Pecosa pec=new Pecosa();
		Ordencompra ordenObtenida=new Ordencompra();
		boolean rpta1,rpta2;
		if(orden!=null){
			//Actualizar campos en la orden de compra.!
			rpta1=this.getOrdencompraDAO().updateCampsFyG(orden);			
			if(rpta1){
				log.info("Orden compra actualizó sus campos : Exito.");
				
			//Para las pecosa!
				if(pecosa != null){
					if(pecosa.getOrdencompra()==null){				
						Long numPec;
						int proyectoId;				
						proyectoId=orden.getId().getProyectoid();
						//Obtengo el numero de la BD
						numPec=obtenerNroSig(proyectoId);
						//////////////////////
						pecId=new PecosaId(numPec,proyectoId);
						pec.setId(pecId);
						//Obtengo luego de salvarlo.
						ordenObtenida=this.getOrdencompraDAO().findById(orden.getId());
						pec.setOrdencompra(ordenObtenida);
						////////////////////////////
						pec.setPecobs(pecosa.getPecobs());
						pec.setPecmontot(ordenObtenida.getTotaloc().doubleValue());
						pec.setPecestado(pecosa.getPecestado());
						pec.setPecdes(pecosa.getPecdes());						
						pec.setPecdesdir(pecosa.getPecdesdir());
						pec.setFechapec(pecosa.getFechapec());
						pec.setSerie("");
						pec.setOcnro(ordenObtenida.getId().getOcnro());
						pec.setPreimpresoinicial(pecosa.getPreimpresoinicial());						
						
						rpta2=this.getPecosaDAO().save(pec);
						if(rpta2){
							log.info("Pecosa grabada con exito");
							return true;
						}else{
							log.info("Pecosa grabada sin exito");
							return false;
						}
					}
					
				}
			}
			else{
				log.info("Orden compra no actualizó sus campos");
				return false;
			}
		}	
		return false;
		}
	
	@Override
	public boolean validar(Pecosa pecosa, Ordencompra orden) {//Estado actualizado: VALIDADO
		// TODO Auto-generated method stub	
		boolean rpta1 =this.getPecosaDAO().update(pecosa);
		boolean rpta2 =this.getOrdencompraDAO().updateFinal(orden);
			if(rpta1&&rpta2)
				return true;
			else
				return false;
	}	
	
	@Override
	public boolean update(Pecosa ordencompra) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean delete(Pecosa ordencompra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pecosa findByObject(Pecosa ordencompra) {
		// TODO Auto-generated method stub	
		return null;
	}

	@Override
	public List<Pecosa> findByLikeObject(Pecosa ordencompra) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String obtenerNroSiguiente(int proyectoId) {
		// TODO Auto-generated method stub
		/**
		 * @formatNumber 131-2014-000001
		 */
		int tam ;
		String correl="";//Correlativo de ceros
		Long numCont=Long.parseLong(this.getPecosaDAO().obtenerUltimoNroPreImpreso(proyectoId));
		String num=FormatFINCyt().get(proyectoId);
		String anio=String.valueOf(Calendar.getInstance().get(Calendar.YEAR))+"-";
		tam = ("" + numCont).length();
		for (int i = 0; i < (6 - tam); i++) {
					correl+= "0";
		}
		
		return num+anio+correl+numCont;
	}
	
	private Long obtenerNroSig(int proyectoId) {
		// TODO Auto-generated method stub
		/**
		 * @formatNumber 2014-000001
		 */
		int tam ;
		String numero;
		String correl="";//Correlativo de ceros
		Long numCont=Long.parseLong(this.getPecosaDAO().obtenerUltimoNro(proyectoId));		
		String anio=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		tam = ("" + numCont).length();
		for (int i = 0; i < (6 - tam); i++) {
					correl+= "0";
		}
		numero=anio+correl+numCont;
		return Long.parseLong(numero);
	}
	
	@Override
	public Long obtenerUltimoNroPreImpreso() {
		// TODO Auto-generated method stub
		//return this.getPecosaDAO().obtenerUltimoNroPreImpreso();
		return null;
	}
	
	private Map<Integer,String> FormatFINCyt(){
		Map <Integer,String> contratos=new HashMap<Integer,String>();
		/**
		 * Concursos Fincyt
		 * pOR Codigo de contratoS
		 */
		/**
		 * key:proyectoid
		 * llave:contrato Nº
		 */
		contratos.put(1, "131-");
		contratos.put(2, "132-");
		contratos.put(3, "133-");
		contratos.put(4, "134-");
		contratos.put(5, "135-");
		contratos.put(6, "173-");
		contratos.put(7, "180-");
		contratos.put(8, "186-");
		contratos.put(9, "188-");
		contratos.put(10,"230-");
		contratos.put(11,"238-");	
		return contratos;
	}
	public PecosaDAO getPecosaDAO() {
		return pecosaDAO;
	}

	public void setPecosaDAO(PecosaDAO pecosaDAO) {
		this.pecosaDAO = pecosaDAO;
	}

	@Override
	public List<Pecosa> getAll(int proyectoId) {
		// TODO Auto-generated method stub
		return this.getPecosaDAO().getAll(proyectoId);
	}
	@Override
	public Pecosa findById(PecosaId id) {
		// TODO Auto-generated method stub
		return this.getPecosaDAO().findById(id);
		
	}

	public OrdencompraDAO getOrdencompraDAO() {
		return ordencompraDAO;
	}

	public void setOrdencompraDAO(OrdencompraDAO ordencompraDAO) {
		this.ordencompraDAO = ordencompraDAO;
	}

	@Override
	public boolean updateOperacionesEditarPecosa(Pecosa pecosa,Ordencompra orden) {
		// TODO Auto-generated method stub
		
		
		if(pecosaDAO.updateCampsEnEditarPecosa(pecosa)){
			ordencompraDAO.updateCampsEnEditarPecosa(orden);
			return true;
		}else {
			log.info("No se pudo modificar la pecosa :(.");
			return false;
		}
		
		
	}

}
