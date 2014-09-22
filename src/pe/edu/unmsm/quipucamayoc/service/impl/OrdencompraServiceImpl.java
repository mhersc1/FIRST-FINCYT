package pe.edu.unmsm.quipucamayoc.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.unmsm.quipucamayoc.dao.inf.ArticuloDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.CotizacionproveedorDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.DetalleocDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.MarcaDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.OrdencompraDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.ProveedorDAO;
import pe.edu.unmsm.quipucamayoc.dao.inf.UniMedArtDAO;
import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Cotizacionproveedor;
import pe.edu.unmsm.quipucamayoc.model.CotizacionproveedorId;
import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.DetalleocId;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;
import pe.edu.unmsm.quipucamayoc.service.inf.OrdencompraService;
/**
 * 
 * @author Mher
 * Service para las Ordenes de compra
 */

@Service("ordencompraService")
public class OrdencompraServiceImpl implements OrdencompraService{
	
	@Autowired
	private OrdencompraDAO ordencompraDAO;	
	@Autowired
	private ProveedorDAO proveedorDAO;
	@Autowired
	private ArticuloDAO articuloDAO;
	@Autowired
	private MarcaDAO marcaDAO;
	@Autowired
	private UniMedArtDAO uniMedArtDAO;	
	@Autowired
	private DetalleocDAO detalleocDAO;
	@Autowired
	private CotizacionproveedorDAO cotizacionproveedorDAO;
	
	public boolean save(Ordencompra ordenCompra,List<Detalleoc> detalles) {
		// TODO Auto-generated method stub
		Cotizacionproveedor cotOrig=null;//Original
		Cotizacionproveedor cotProv=null;//Recorre
		CotizacionproveedorId cotId=null;
		Ordencompra ordencompra=new Ordencompra();
		OrdencompraId ocId=null;
				
		if (ordenCompra!=null) {
			if(ordenCompra.getCotizacionproveedor()!=null){
				cotOrig=ordenCompra.getCotizacionproveedor();
				String numCotProv;
				boolean rptaCotiz;
				numCotProv=cotizacionproveedorDAO.getSiguienteId('C');
				//********************INIT Cotizacion Proveedor ********************
				//Para el id
				cotId=new CotizacionproveedorId(numCotProv,'C');				
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
					//********************INIT Orden Compra ********************
					String numOrden;
					proyectoId = ordenCompra.getId().getProyectoid();
					boolean rptaOrden;
					//Para el id y el numeroPreimpreso					
					numOrden = obtenerNroSig(proyectoId);
					ocId=new OrdencompraId(numOrden, proyectoId);
					ordencompra.setId(ocId);
					ordencompra.setCotizacionproveedor(cotProv);
					ordencompra.setReferencia(ordenCompra.getReferencia());
					ordencompra.setSolicitante(ordenCompra.getSolicitante());
					ordencompra.setFechaoc(ordenCompra.getFechaoc());
					ordencompra.setFechaimp(ordenCompra.getFechaimp());
					ordencompra.setTotaloc(ordenCompra.getTotaloc());
					ordencompra.setAlmdir(ordenCompra.getAlmdir());
					ordencompra.setObservacion(ordenCompra.getObservacion());
					ordencompra.setOcestado(ordenCompra.getOcestado());
					ordencompra.setOcpreimpresoini(ordenCompra.getOcpreimpresoini());
					ordencompra.setTotaligv(ordenCompra.getTotaligv());
					
					rptaOrden=ordencompraDAO.save(ordencompra);
					//********************END Orden Compra ********************
					
					//********************INIT DETALLES ********************
					if(rptaOrden){
						if (detalles != null && detalles.size() > 0) {
							for(Detalleoc detalle:detalles){
								//Sale por alguna tonteria debido al for;
								if(registrarDetalleoc(ordencompra,detalle,proyectoId)==false){break;};
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
	public boolean validar(Ordencompra ordencompra) {//Estado actualizado: VALIDADO
		// TODO Auto-generated method stub	
			if(ordencompraDAO.update(ordencompra))
			return true;
			else
			return false;
	}
	
	private boolean registrarDetalleoc(Ordencompra ordencompra,Detalleoc detalle,int proyectoId){
		boolean rptaDet;
		Detalleoc detalleTmp=new Detalleoc();
		Long detocid=detalleocDAO.getSiguienteDetOcId();
		
		detalleTmp.setId(new DetalleocId(detocid,proyectoId));
		detalleTmp.setOrdencompra(ordencompra);
		detalleTmp.setEspecifica(detalle.getEspecifica());
		detalleTmp.setImpuesto(detalle.getImpuesto());		
		detalleTmp.setArtcod(detalle.getArtcod());
		detalleTmp.setMarca(detalle.getMarca());		
		detalleTmp.setMoneda(detalle.getMoneda());
		detalleTmp.setCantidad(detalle.getCantidad());
		detalleTmp.setPreciounit(detalle.getPreciounit());
		detalleTmp.setTipoprecio(detalle.getTipoprecio());
		detalleTmp.setSubtotal(detalle.getSubtotal());
		detalleTmp.setIgv(detalle.getIgv());
		detalleTmp.setArtdes(detalle.getArtdes());
		detalleTmp.setDetalle(detalle.getDetalle());
		detalleTmp.setUnidmed(detalle.getUnidmed());
		detalleTmp.setOcnro(ordencompra.getId().getOcnro());//ADSAFASFASFAS		
		rptaDet=detalleocDAO.save(detalleTmp);
		return rptaDet;
	}
	
	@Override
	public boolean updateOperacionesEditarOC(Ordencompra ordencompra,List<Detalleoc> detalles) {		
		// TODO Auto-generated method stub		
		try {			
			for(Detalleoc detalle:detalles){
				detalleocDAO.updateCampsEnEditarOC(detalle);			
			}			
			ordencompraDAO.updateCampsEnEditarOC(ordencompra);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
	}
	
	@Override
	public boolean delete(Ordencompra ordencompra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ordencompra findByObject(Ordencompra ordencompra) {
		// TODO Auto-generated method stub	
		return null;
	}

	@Override
	public List<Ordencompra> findByLikeObject(Ordencompra ordencompra) {
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
		Long numCont=Long.parseLong(ordencompraDAO.obtenerUltimoNroPreImpreso(proyectoId));
		String num=FormatFINCyt().get(proyectoId);
		String anio=String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1)+"-";
		tam = ("" + numCont).length();
		for (int i = 0; i < (6 - tam); i++) {
					correl+= "0";
		}
		
		return num+anio+correl+numCont;
	}
	
	private String obtenerNroSig(int proyectoId) {
		// TODO Auto-generated method stub
		/**
		 * @formatNumber 2014-000001
		 */
		int tam ;
		String correl="";//Correlativo de ceros
		Long numCont=Long.parseLong(ordencompraDAO.obtenerUltimoNro(proyectoId));		
		String anio=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		tam = ("" + numCont).length();
		for (int i = 0; i < (6 - tam); i++) {
					correl+= "0";
		}
		
		return anio+correl+numCont;
	}
	
	@Override
	public Long obtenerUltimoNroPreImpreso() {
		// TODO Auto-generated method stub
		//return ordencompraDAO().obtenerUltimoNroPreImpreso();
		return null;
	}

	@Override
	public List<Proveedor> findByProveedor(Proveedor proveedor,int modo) {
		// TODO Auto-generated method stub
		if(modo==0)
		return this.getProveedorDAO().findByRUC(proveedor.getProruc());
		else
		return this.getProveedorDAO().findByRazSoc(proveedor.getProrazsoc());
		
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
	@Override
	public List<Proveedor> getAllProveedors() {
		// TODO Auto-generated method stub
		return this.getProveedorDAO().getAll();
	}
	
	@Override
	public List<Articulo> getAllArticulos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> getAllMarcas() {
		// TODO Auto-generated method stub
		return this.getMarcaDAO().getAll();
	}
	
	@Override
	public List<Articulo> findByArticulo(Articulo articulo) {
		// TODO Auto-generated method stub
		return this.articuloDAO.findByArticulo(articulo.getArtdes(),"B");
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
	public List<Ordencompra> getAll(int proyectoId) {
		// TODO Auto-generated method stub
		return ordencompraDAO.getAll(proyectoId);
	}
	@Override
	public Ordencompra findById(OrdencompraId id) {
		// TODO Auto-generated method stub
		return ordencompraDAO.findById(id);
		
	}
	public UniMedArtDAO getUniMedArtDAO() {
		return uniMedArtDAO;
	}
	public void setUniMedArtDAO(UniMedArtDAO uniMedArtDAO) {
		this.uniMedArtDAO = uniMedArtDAO;
	}
	@Override
	public List<String> getAllUnidades() {
		// TODO Auto-generated method stub
		return this.getUniMedArtDAO().getAll();
	}


}
