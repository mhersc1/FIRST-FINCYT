package pe.edu.unmsm.quipucamayoc.web.mb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.Detalleos;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReporteOC;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReporteOS;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReportePecosa;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.Metodos;

@Controller
@Scope("session")
public class AsistenciaMB{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OrdencompraMB orden;
	@Autowired
	private UsuarioFinzytMB user;
	@Autowired
	private PdfMB pdfMB;
	@Autowired
	private OrdenservicioMB servicio;
	@Autowired
	private PecosaMB pecosa;
	
	private Ordencompra ordenReport;
	private Ordenservicio servicioReport;
	private Pecosa pecosaReport;
	
	public void asistenciaPDF(){
		String reporte="OrdenCompraSinFormato";
		pdfMB.generarPdf(reporte, obtenerParametrosReporte(), obtenerArticulosReporte());
	}
	
	public void asistenciaPDFServicio(){
		String reporte="OrdenServicioSinFormato";
		pdfMB.generarPdfServicio(reporte, obtenerParametrosReporteServicio(), obtenerArticulosReporteServicio());
	}
	
	public void asistenciaPDFPecosa(){
		String reporte = "reporte_pecosa_sinformato";
		pdfMB.generarPdfPecosa(reporte, obtenerParametrosReportePecosa(), obtenerArticulosReportePecosa());
	}
	
	public Map<String,Object> obtenerParametrosReporte(){
        
		Map<String, Object> parametrosReport= new HashMap<String, Object>();		
		
		ordenReport=orden.getOrdenDecompra();
		
      try{
        //Indicadores Reporte OC
        parametrosReport.put("dia",""+ordenReport.getFechaoc().getDate());        
        parametrosReport.put("mes",""+(ordenReport.getFechaoc().getMonth()+1));        
        parametrosReport.put("anio",""+(ordenReport.getFechaoc().getYear()+1900));
        System.out.println("Anio de melas!!"+ordenReport.getFechaoc().getYear());
		parametrosReport.put("ocnro",ordenReport.getOcpreimpresoini());
		System.out.print("Numero:"+ordenReport.getId().getOcnro());
		
		//Indicadores Body OC
		FacesContext context=FacesContext.getCurrentInstance();
		ServletContext servletContex=(ServletContext) context.getExternalContext().getContext();
		String direccion=servletContex.getRealPath("/images/unmsm.gif");
		
		parametrosReport.put("bandera", "0");
		parametrosReport.put("Logo", direccion);
		parametrosReport.put("Titulo","ORDEN DE COMPRA - GUIA DE INTERNAMIENTO");
		parametrosReport.put("usunom", user.getUser());		
		parametrosReport.put("prorazsoc",ordenReport.getCotizacionproveedor().getRazonsocial() );
		parametrosReport.put("prodir",ordenReport.getCotizacionproveedor().getProdir());
		parametrosReport.put("proruc",ordenReport.getCotizacionproveedor().getRuc());
		parametrosReport.put("formaPago",ordenReport.getCotizacionproveedor().getFormapago());
		parametrosReport.put("entrega", ordenReport.getCotizacionproveedor().getTiempoentrega());
		parametrosReport.put("garantia", ordenReport.getCotizacionproveedor().getGarantia());		
		parametrosReport.put("igvTotal",ordenReport.getTotaligv().toString());
		parametrosReport.put("totalOC",ordenReport.getTotaloc().toString());
		parametrosReport.put("totalOCLetras", String.valueOf(Metodos.convertirNumeroLetras(ordenReport.getTotaloc(), "NUEVOS SOLES")));						
		parametrosReport.put("obs",ordenReport.getObservacion());		
		parametrosReport.put("codigoFac",String.valueOf(ordenReport.getId().getProyectoid()));		
		parametrosReport.put("solicitante","NO DATA");		
		parametrosReport.put("porcIgv","18%");					
	}catch (Exception e) {
		e.printStackTrace();
	}		
	return parametrosReport;		
	}
	
public Map<String,Object> obtenerParametrosReporteServicio(){
        
		Map<String, Object> parametrosReport= new HashMap<String, Object>();		
		
		servicioReport=servicio.getOrdenDeservicio();
		
      try{
        //Indicadores Reporte OC
        parametrosReport.put("dia",""+servicioReport.getFechaos().getDate());        
        parametrosReport.put("mes",""+(servicioReport.getFechaos().getMonth()+1));        
        parametrosReport.put("anio",""+(servicioReport.getFechaos().getYear()+1900));
        System.out.println("Anio de melas!!"+servicioReport.getFechaos().getYear());
		parametrosReport.put("osnro",servicioReport.getOspreimpresoini());
		System.out.print("Numero:"+servicioReport.getId().getOsnro());
		
		//Indicadores Body OC
		FacesContext context=FacesContext.getCurrentInstance();
		ServletContext servletContex=(ServletContext) context.getExternalContext().getContext();
		String direccion=servletContex.getRealPath("/images/unmsm.gif");
		
		parametrosReport.put("bandera", "0");
		parametrosReport.put("Logo", direccion);
		parametrosReport.put("Titulo","ORDEN DE SERVICIO - GUIA DE INTERNAMIENTO");
		parametrosReport.put("usunom", user.getUser());		
		parametrosReport.put("prorazsoc",servicioReport.getCotizacionproveedor().getRazonsocial() );
		parametrosReport.put("proruc",servicioReport.getCotizacionproveedor().getRuc());
		parametrosReport.put("prodir",servicioReport.getCotizacionproveedor().getProdir());		
		parametrosReport.put("formaPago",servicioReport.getCotizacionproveedor().getFormapago());
		parametrosReport.put("entrega", servicioReport.getCotizacionproveedor().getTiempoentrega());
		parametrosReport.put("garantia", servicioReport.getCotizacionproveedor().getGarantia());		
		parametrosReport.put("igvTotal",servicioReport.getTotaligv().toString());
		parametrosReport.put("totalOS",servicioReport.getTotalos().toString());
		parametrosReport.put("totalOSLetras", String.valueOf(Metodos.convertirNumeroLetras(servicioReport.getTotalos(), "NUEVOS SOLES")));						
		parametrosReport.put("obs",servicioReport.getObservacion());		
		parametrosReport.put("codigoFac",String.valueOf(servicioReport.getId().getProyectoid()));		
		parametrosReport.put("solicitante","-");		
		parametrosReport.put("porcIgv","18");
	}catch (Exception e) {
		e.printStackTrace();
	}		
	return parametrosReport;		
	}
		
	public Map<String,Object> obtenerParametrosReportePecosa(){
	    
		Map<String, Object> parametrosReport= new HashMap<String, Object>();		
		
		
		pecosaReport = pecosa.getPecosa();
		ordenReport = orden.getOrdenDecompra();
		
		  try{
		    //Indicadores Reporte OC
		    parametrosReport.put("dependenciasolicitante","DependenciaSolicitante");
		    parametrosReport.put("entregara", pecosaReport.getPecdes());
		    parametrosReport.put("destinoa", pecosaReport.getPecdesdir());
		    //
		    parametrosReport.put("codigopecosa",pecosaReport.getId().getPecnro().toString());
		    parametrosReport.put("pedido","-");
		    parametrosReport.put("preimpresoInicialCadena",pecosaReport.getPreimpresoinicial());
		    parametrosReport.put("dependenciacodigo","");
		    //
		    parametrosReport.put("exped",pecosaReport.getOrdencompra().getReferencia());
		    //
		    parametrosReport.put("orden_de_compra",pecosaReport.getOcnro());
		    parametrosReport.put("observaciones", pecosaReport.getPecobs());
		    //
		    parametrosReport.put("informenro","-");
			parametrosReport.put("proveedor",pecosaReport.getOrdencompra().getCotizacionproveedor().getRazonsocial());
			parametrosReport.put("guia",pecosaReport.getOrdencompra().getGuiaDeRemision());
			parametrosReport.put("factura",pecosaReport.getOrdencompra().getFactura());
			parametrosReport.put("tipo_de_pecosa","TRANSITO");
		    //		    
			int dia = pecosaReport.getFechapec().getDay();
			int mes = pecosaReport.getFechapec().getMonth()+1;
			int anio = pecosaReport.getFechapec().getYear()+1900;
			String fechaPec = dia+"/"+mes+"/"+anio;
			System.out.println(fechaPec);
		    parametrosReport.put("fechapec",pecosaReport.getFechapec());        
			parametrosReport.put("totalPecosa", pecosaReport.getOrdencompra().getTotaloc().toString());
			parametrosReport.put("itemInclusive", "");
			
			//Indicadores Body OC
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return parametrosReport;		
	}

	public Collection<ArticuloReporteOC> obtenerArticulosReporte(){
		List<Detalleoc> detalles=new ArrayList<Detalleoc>();
		Collection<ArticuloReporteOC> articulosReporte= new ArrayList<ArticuloReporteOC>();
		ordenReport=orden.getOrdenDecompra();
		detalles.addAll(ordenReport.getDetalleocs());
		try{		
		for(Detalleoc gen:detalles){			
			ArticuloReporteOC articulo= new ArticuloReporteOC();			
			articulo.setUniMed("NO DATA");//Esto hay que jalar de la tabla unimed
			articulo.setDescripcion(gen.getArtdes());
			articulo.setIdArticulo(gen.getEspecifica());
			articulo.setCantidad(gen.getCantidad().toString());				
			articulo.setMarca(gen.getMarca());
			articulo.setMoneda(String.valueOf(gen.getMoneda()));			
			articulo.setPrecioUnit(Metodos.formatearDecimales(gen.getPreciounit().toString(),4));			
			articulo.setPrecioTotal(Metodos.roundToStringDinero(gen.getSubtotal()));	
			articulo.setPrecioTotalAux("10.56");
			articulo.setTipoPrecio(String.valueOf(gen.getTipoprecio()));
			articulo.setCaracteristicas(gen.getDetalle());
			articulo.setDetalle(gen.getDetalle().toString());
			if(gen.getUnidmed()!=null){
				articulo.setUniMed(gen.getUnidmed().toString());
			}
			articulosReporte.add(articulo);
		}		
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return articulosReporte;				
	}
	
	public Collection<ArticuloReporteOS> obtenerArticulosReporteServicio(){
		List<Detalleos> detalles=new ArrayList<Detalleos>();
		Collection<ArticuloReporteOS> articulosReporte= new ArrayList<ArticuloReporteOS>();
		servicioReport=servicio.getOrdenDeservicio();
		detalles.addAll(servicioReport.getDetalleoses());
		try{		
		for(Detalleos gen:detalles){			
			ArticuloReporteOS articulo= new ArticuloReporteOS();
			articulo.setDescripcion(gen.getArtdes());
			articulo.setIdArticulo(gen.getEspecifica());
			articulo.setMoneda(String.valueOf(gen.getMoneda()));			
			articulo.setPrecioUnit(Metodos.formatearDecimales(gen.getPreciounit().toString(),4));	
			articulo.setIgv(gen.getIgv().toString());
			
			articulo.setPrecioTotal(Metodos.roundToStringDinero(gen.getSubtotal()));	
			articulo.setPrecioTotalAux("10.56");
			articulo.setTipoPrecio(String.valueOf(gen.getTipoprecio()));
			articulo.setCaracteristicas(gen.getDetalle());
			articulo.setDetalle(gen.getDetalle().toString());
			articulo.setTipoCambio("1.00");
			articulosReporte.add(articulo);
		}		
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return articulosReporte;				
}
	
	public Collection<ArticuloReportePecosa> obtenerArticulosReportePecosa(){
		List<Detalleoc> detalles=new ArrayList<Detalleoc>();
		Collection<ArticuloReportePecosa> articulosReporte= new ArrayList<ArticuloReportePecosa>();
		//ordenReport=orden.getOrdenDecompra();
		//detalles.addAll(ordenReport.getDetalleocs());
		
		pecosaReport = pecosa.getPecosa();
		detalles.addAll(pecosaReport.getOrdencompra().getDetalleocs());
		System.out.println("tamaño de detalle: "+detalles.size());
		try{		
		for(Detalleoc gen:detalles){			
			ArticuloReportePecosa articulo= new ArticuloReportePecosa();			
			articulo.setUniMed("NO DATA");//Esto hay que jalar de la tabla unimed
			articulo.setDescripcion(gen.getArtdes());
			articulo.setIdArticulo(gen.getEspecifica());
			articulo.setCantidad(gen.getCantidad().longValue());				
			articulo.setMarca(gen.getMarca());
			articulo.setMoneda(String.valueOf(gen.getMoneda()));			
			articulo.setPrecioUnit(new BigDecimal(Metodos.formatearDecimales(gen.getPreciounit().toString(),4)));			
			articulo.setPrecioTotal(gen.getSubtotal());
			articulo.setTipoPrecio(String.valueOf(gen.getTipoprecio()));
			
			articulo.setCaracteristicas(gen.getDetalle());
			articulo.setDetalle(gen.getDetalle().toString());
			articulo.setPrecioTotalAux("1.18");
			if(gen.getUnidmed()!=null){
				articulo.setUniMed(gen.getUnidmed().toString());
			}
			articulosReporte.add(articulo);
		}		
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return articulosReporte;				
	}
	
	
	public Ordenservicio getServicioReport() {
		return servicioReport;
	}
	public void setServicioReport(Ordenservicio servicioReport) {
		this.servicioReport = servicioReport;
	}
}
