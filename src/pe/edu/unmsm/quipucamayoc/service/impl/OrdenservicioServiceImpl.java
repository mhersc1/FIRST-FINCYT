package pe.edu.unmsm.quipucamayoc.service.impl;

import pe.edu.unmsm.quipucamayoc.service.inf.OrdenservicioService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.quipucamayoc.dao.inf.ArticuloDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.CotizacionproveedorDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.DetalleocDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.DetalleosDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.MarcaDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.OrdencompraDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.OrdenservicioDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.ProveedorDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.UniMedArtDAO;
import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Cotizacionproveedor;
import pe.edu.unmsm.quipucamayoc.model.CotizacionproveedorId;
import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.DetalleocId;
import pe.edu.unmsm.quipucamayoc.model.Detalleos;
import pe.edu.unmsm.quipucamayoc.model.DetalleosId;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.model.OrdenservicioId;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;
import pe.edu.unmsm.quipucamayoc.service.inf.OrdenservicioService;

/**
 * 
 * @author Ruddy
 * Service para las Ordenes de servicio
 */
@Service("ordenservicioService")
public class OrdenservicioServiceImpl implements OrdenservicioService{

	@Autowired
	private OrdenservicioDAO ordenservicioDAO;	
	@Autowired
	private ProveedorDAO proveedorDAO;
	@Autowired
	private ArticuloDAO articuloDAO;
	@Autowired
	private MarcaDAO marcaDAO;
	@Autowired
	private UniMedArtDAO uniMedArtDAO;	
	@Autowired
	private DetalleosDAO detalleosDAO;
	@Autowired
	private CotizacionproveedorDAO cotizacionproveedorDAO;
	
	
	@Override
	public boolean save(Ordenservicio ordenServicio, List<Detalleos> detalles,
			char estado) {
		// TODO Auto-generated method stub
		Cotizacionproveedor cotOrig=null;//Original
		Cotizacionproveedor cotProv=null;//Recorre
		CotizacionproveedorId cotId=null;
		Ordenservicio ordenservicio=new Ordenservicio();
		OrdenservicioId osId=null;
				
		if (ordenServicio!=null) {
			if(ordenServicio.getCotizacionproveedor()!=null){
				cotOrig=ordenServicio.getCotizacionproveedor();
				String numCotProv;
				boolean rptaCotiz;
				numCotProv=cotizacionproveedorDAO.getSiguienteId('S');
				//********************INIT Cotizacion Proveedor ********************
				//Para el id
				cotId=new CotizacionproveedorId(numCotProv,'S');				
				cotProv = new Cotizacionproveedor();
				cotProv.setId(cotId);
				cotProv.setRuc(cotOrig.getRuc());
				cotProv.setDetallecot(cotOrig.getDetallecot());
				cotProv.setProdir(cotOrig.getProdir());				
				cotProv.setRazonsocial(cotOrig.getRazonsocial());
				cotProv.setProtel(cotOrig.getProtel());
				cotProv.setGarantia(cotOrig.getGarantia());
				cotProv.setFormapago(cotOrig.getFormapago());
				cotProv.setTiempoentrega(cotOrig.getTiempoentrega());
				
				rptaCotiz=cotizacionproveedorDAO.save(cotProv);
				//********************End Cotizacion Proveedor ********************
				
				int proyectoId;
				if (rptaCotiz) {
					//********************INIT Orden Servicio ********************
					String numOrden;
					proyectoId = ordenServicio.getId().getProyectoid();
					boolean rptaOrden;
					//Para el id y el numeroPreimpreso					
					numOrden = obtenerNroSig(proyectoId);
					osId=new OrdenservicioId(numOrden, proyectoId);
					ordenservicio.setId(osId);
					ordenservicio.setCotizacionproveedor(cotProv);
					ordenservicio.setReferencia(ordenServicio.getReferencia());
					ordenservicio.setSolicitante(ordenServicio.getSolicitante());
					ordenservicio.setFechaos(ordenServicio.getFechaos());
					ordenservicio.setFechaimp(ordenServicio.getFechaimp());
					ordenservicio.setTotalos(ordenServicio.getTotalos());
					ordenservicio.setObservacion(ordenServicio.getObservacion());
					ordenservicio.setOsestado(ordenServicio.getOsestado());
					ordenservicio.setOspreimpresoini(ordenServicio.getOspreimpresoini());
					ordenservicio.setTotaligv(ordenServicio.getTotaligv());
					
					rptaOrden=ordenservicioDAO.save(ordenservicio);
					//********************END Orden Servicio ********************
					
					//********************INIT DETALLES ********************
					if(rptaOrden){
						if (detalles != null && detalles.size() > 0) {
							for(Detalleos detalle:detalles){
								//Sale por alguna tonteria debido al for;
								if(registrarDetalleos(ordenservicio,detalle,proyectoId)==false){break;};
								}
						}
						
					}
					//********************END DETALLES********************
				}
			}
		
			}
		return true;
	}
	
	@Override
	public boolean validar(Ordenservicio ordenservicio) {//Estado actualizado: VALIDADO
		// TODO Auto-generated method stub	
			if(this.getOrdenservicioDAO().update(ordenservicio))
			return true;
			else
			return false;
	}
	
	@Override
	public boolean update(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ordenservicio findByObject(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ordenservicio findById(OrdenservicioId id) {
		// TODO Auto-generated method stub
		return this.getOrdenservicioDAO().findById(id);
	}

	@Override
	public List<Ordenservicio> getAll(int proyectoId) {
		// TODO Auto-generated method stub
		return this.getOrdenservicioDAO().getAll(proyectoId);
	}

	@Override
	public String obtenerNroSiguiente(int proyectoId) {
		// TODO Auto-generated method stub
		/**
		 * @formatNumber 131-2014-000001
		 */
		int tam ;
		String correl="";//Correlativo de ceros
		Long numCont=Long.parseLong(this.getOrdenservicioDAO().obtenerUltimoNroPreImpreso(proyectoId));
		String num=FormatFINCyt().get(proyectoId);
		String anio=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1)+"-";
		tam = ("" + numCont).length();
		for (int i = 0; i < (6 - tam); i++) {
					correl+= "0";
		}
		
		return num+anio+correl+numCont;
	}

	@Override
	public Long obtenerUltimoNroPreImpreso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ordenservicio> findByLikeObject(Ordenservicio ordenservicio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Proveedor> getAllProveedors() {
		// TODO Auto-generated method stub
		return this.getProveedorDAO().getAll();
	}

	@Override
	public List<Proveedor> findByProveedor(Proveedor proveedor, int modo) {
		// TODO Auto-generated method stub
		if(modo==0)
			return this.getProveedorDAO().findByRUC(proveedor.getProruc());
		else
			return this.getProveedorDAO().findByRazSoc(proveedor.getProrazsoc());
	}

	@Override
	public List<Articulo> getAllArticulos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Articulo> findByArticulo(Articulo articulo) {
		// TODO Auto-generated method stub
		return this.articuloDAO.findByArticulo(articulo.getArtdes(),"S");
	}

	@Override
	public List<String> getAllMarcas() {
		// TODO Auto-generated method stub
		return this.getMarcaDAO().getAll();
	}

	@Override
	public List<String> getAllUnidades() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String obtenerNroSig(int proyectoId) {
		// TODO Auto-generated method stub
		/**
		 * @formatNumber 2014-000001
		 */
		int tam ;
		String correl="";//Correlativo de ceros
		Long numCont=Long.parseLong(this.getOrdenservicioDAO().obtenerUltimoNro(proyectoId));		
		String anio=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		tam = ("" + numCont).length();
		for (int i = 0; i < (6 - tam); i++) {
					correl+= "0";
		}
		
		return anio+correl+numCont;
	}
	
	private boolean registrarDetalleos(Ordenservicio ordenservicio,Detalleos detalle,int proyectoId){
		boolean rptaDet;
		Detalleos detalleTmp=new Detalleos();
		Long detosid=detalleosDAO.getSiguienteDetOsId();
		
		detalleTmp.setId(new DetalleosId(detosid,proyectoId));
		detalleTmp.setOrdenservicio(ordenservicio);
		detalleTmp.setEspecifica(detalle.getEspecifica());
		detalleTmp.setImpuesto(detalle.getImpuesto());		
		detalleTmp.setArtcod(detalle.getArtcod());
		detalleTmp.setArtdes(detalle.getArtdes());
		detalleTmp.setMoneda(detalle.getMoneda());
		detalleTmp.setPreciounit(detalle.getPreciounit());
		detalleTmp.setTipoprecio(detalle.getTipoprecio());
		detalleTmp.setSubtotal(detalle.getSubtotal());
		detalleTmp.setIgv(detalle.getIgv());
		detalleTmp.setDetalle(detalle.getDetalle());
		detalleTmp.setOsnro(ordenservicio.getId().getOsnro());
		rptaDet=detalleosDAO.save(detalleTmp);
		return rptaDet;
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
	
	public OrdenservicioDAO getOrdenservicioDAO() {
		return ordenservicioDAO;
	}

	public void setOrdenservicioDAO(OrdenservicioDAO ordenservicioDAO) {
		this.ordenservicioDAO = ordenservicioDAO;
	}

	public ProveedorDAO getProveedorDAO() {
		return proveedorDAO;
	}

	public void setProveedorDAO(ProveedorDAO proveedorDAO) {
		this.proveedorDAO = proveedorDAO;
	}


	public ArticuloDAO getArticuloDAO() {
		return articuloDAO;
	}

	public void setArticuloDAO(ArticuloDAO articuloDAO) {
		this.articuloDAO = articuloDAO;
	}

	public MarcaDAO getMarcaDAO() {
		return marcaDAO;
	}

	public void setMarcaDAO(MarcaDAO marcaDAO) {
		this.marcaDAO = marcaDAO;
	}

	@Override
	public boolean updateOperacionesEditarOS(Ordenservicio ordenservicio,List<Detalleos> detalles) {
		// TODO Auto-generated method stub
		try {
			for(Detalleos detalle:detalles){
				detalleosDAO.updateCampsEnEditarOS(detalle);
			}
			ordenservicioDAO.updateCampsEnEditarOS(ordenservicio);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
