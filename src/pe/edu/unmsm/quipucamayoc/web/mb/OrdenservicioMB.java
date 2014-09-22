package pe.edu.unmsm.quipucamayoc.web.mb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pe.edu.unmsm.quipucamayoc.model.Articulo;
import pe.edu.unmsm.quipucamayoc.model.Cotizacionproveedor;
import pe.edu.unmsm.quipucamayoc.model.Detalleos;
import pe.edu.unmsm.quipucamayoc.model.Ordenservicio;
import pe.edu.unmsm.quipucamayoc.model.OrdenservicioId;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;
import pe.edu.unmsm.quipucamayoc.service.impl.OrdenservicioServiceImpl;
import pe.edu.unmsm.quipucamayoc.service.inf.OrdenservicioService;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HabilitadosySeleccionados;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.Metodos;

@Controller
@Scope("session")
public class OrdenservicioMB {

	@Autowired
	private OrdenservicioService ordenservicioService;
	private Ordenservicio ordenservicio;
	private Proveedor proveedor;
	private Cotizacionproveedor cotizacionproveedor;
	private List<Detalleos> detallesos;
	private Detalleos detalleos;
	private ArrayList<Articulo> articulos;
	private Articulo articulo;
	private HabilitadosySeleccionados habSel;
	private List<String> marcas;
	private List<String> unidades;
	private int seleccion; //seleccion de proveedor
	
	private String datoProveedor;
	private String descripcionArticulo;
	
	private BigDecimal montoTotal;
	/**
	 * En el caso de las consultas:
	 */
	private List<Ordenservicio> ordenes;
	private Ordenservicio ordenDeservicio;
	private List<Detalleos> detalles;
	
	@Autowired
	private UsuarioFinzytMB usuarioFinzytMB;
	private OrdenFiltroMB oSFiltroMB;
	
	private Log log=LogFactory.getLog(OrdenservicioServiceImpl.class);
	
	private static String USER_NAME = "quipucamayoc@unmsm.edu.pe";
	private static String PASSWORD = "1234qwer";
	public OrdenservicioMB(){
		super();
		this.init();
	}
	
	public void init(){
		this.setOrdenservicio(new Ordenservicio());
		this.setCotizacionproveedor(new Cotizacionproveedor());
		this.setDetallesos(new ArrayList<Detalleos>());
		this.setDetalleos(new Detalleos());
		this.setProveedor(new Proveedor());
		this.setArticulo(new Articulo());
		this.setMarcas(new ArrayList<String>());
		this.setHabSel(new HabilitadosySeleccionados());
		this.setOrdenDeservicio(new Ordenservicio());
		this.setOrdenes(new ArrayList<Ordenservicio>());
		this.setMontoTotal(new BigDecimal("0.0"));		
	}
	public OrdenservicioMB(Ordenservicio ordenservicio){
		super();
		this.ordenservicio=ordenservicio;
	}
	public void registrarOrdenservicio(){
		/**
		 * @param estado:	0:Generado
		 * 					1:Enviado
		 * 					2:Verificado 					
		 */
		String estado;
		System.out.print(usuarioFinzytMB.getProyecto());
		Ordenservicio ordenservicio=new Ordenservicio();
		List<Detalleos> detalles=new ArrayList<Detalleos>();		
		Cotizacionproveedor cotProv=null;		
		if(cotizacionproveedor!=null){			
			if (proveedor!=null) {
				cotProv = new Cotizacionproveedor();
				cotProv.setRuc(proveedor.getProruc());
				cotProv.setRazonsocial(proveedor.getProrazsoc());
				cotProv.setProdir(proveedor.getProdir());
				cotProv.setProtel(proveedor.getProtel());
				//En los reporte solo mostraremos la razon social
				cotProv.setGarantia(cotizacionproveedor.getGarantia());
				cotProv.setFormapago(cotizacionproveedor.getFormapago());
				cotProv.setTiempoentrega(cotizacionproveedor.getTiempoentrega());				
			}
		}
		
		//Totales IGV Y Orden Compra
		Object[] totales=new Object[2];
		totales=obtenerMontoTotal(detallesos);
		
		estado="1";
		ordenservicio.setReferencia(this.ordenservicio.getReferencia());
		ordenservicio.setSolicitante("Proyecto N∫: "+ habSel.FormatFINCyt().get(usuarioFinzytMB.getProyecto()));
		
		ordenservicio.setId(new OrdenservicioId("",usuarioFinzytMB.getProyecto()));	
		
		if(obtenerFechaOS()!=null){
			Date fechaos=null;
			SimpleDateFormat sample=new SimpleDateFormat("dd/MM/yyyy");
			try {
				fechaos=sample.parse(obtenerFechaOS());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			ordenservicio.setFechaos(fechaos);
		} else{
			ordenservicio.setFechaos(new Date());
		}
		
		
		ordenservicio.setFechaimp(new Date());//Cuando imprima la Orden de compra
		ordenservicio.setTotaligv((BigDecimal)totales[0]);
		ordenservicio.setTotalos((BigDecimal)totales[1]);		
		
		ordenservicio.setObservacion(this.ordenservicio.getObservacion());//Falta aun el campo
		ordenservicio.setOsestado(estado);
		ordenservicio.setOspreimpresoini(this.ordenservicio.getOspreimpresoini());//Lado Servidor
		ordenservicio.setCotizacionproveedor(cotProv);
		
		detalles.addAll(this.getDetallesos());
		boolean enviarEmail =this.getOrdenservicioService().save(ordenservicio, detalles,estado.charAt(0)); 
		if(enviarEmail){
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CAP")){
				rol="CGI";
			}
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="NUEVA ORDEN DE SERVICIO - FINZYT";
			String body="Se ha enviado la Orden de Servicio N∞ "+this.ordenservicio.getOspreimpresoini()+" para ser validada";
			//sendFromEmail(USER_NAME,PASSWORD,to,subject,body);
		}
		
		this.detalleos = new Detalleos();
		this.detallesos = new ArrayList<Detalleos>();
		this.cotizacionproveedor = new Cotizacionproveedor();
		this.ordenservicio = new Ordenservicio();
		this.articulos = new ArrayList<Articulo>();
		this.articulo = new Articulo();
		this.proveedor = new Proveedor();
		this.habSel = new HabilitadosySeleccionados();
		this.datoProveedor = "";
		this.descripcionArticulo="";
		this.setMontoTotal(new BigDecimal("0.0"));
		generarNro();
		
	}
	
/*	public void validarOrdenServicio() throws IOException{		
		Ordenservicio os=new Ordenservicio();
		
		os=this.ordenDeservicio;//OC seleccionada.
		os.setOsestado("2");
		boolean rpta=this.getOrdenservicioService().validar(os);
		if(rpta){
			cargarOrdenesServicio();
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CGI")){
				rol="CAP";
			}
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="LA ORDEN DE SERVICIO "+this.ordenDeservicio.getOspreimpresoini()+" SE HA VALIDADO";
			String body="Se ha validado la Orden de Servicio "+this.ordenDeservicio.getOspreimpresoini()+", ya puede imprimirla.";
			sendFromEmail(USER_NAME,PASSWORD,to,subject,body);
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./listaOCAlt.jsf");
		}else{			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./consultaOrdenDeServicio.jsf");		
		}
	}*/
	public String validarOrdenServicio() {		
		Ordenservicio os=new Ordenservicio();
		
		os=this.ordenDeservicio;//OC seleccionada.
		os.setOsestado("2");
		boolean rpta=this.getOrdenservicioService().validar(os);
		if(rpta){
			cargarOrdenesServicio();
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CGI")){
				rol="CAP";
			}
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="LA ORDEN DE SERVICIO "+this.ordenDeservicio.getOspreimpresoini()+" SE HA VALIDADO";
			String body="Se ha validado la Orden de Servicio "+this.ordenDeservicio.getOspreimpresoini()+", ya puede imprimirla.";
			//sendFromEmail(USER_NAME,PASSWORD,to,subject,body);
			return "listadoOS";
		}else{			
			return "consultaOrdenDeServicio";
		}
	}
	Comparator<Ordenservicio> comparator=new Comparator<Ordenservicio>(){
		@Override
		public int compare(Ordenservicio a, Ordenservicio b) {
			// TODO Auto-generated method stub
			Long numa=Long.parseLong(a.getId().getOsnro());
			Long numb=Long.parseLong(b.getId().getOsnro());
			return (int)(numa-numb);
		}
	};
	@Deprecated
	private static void sendFromEmail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
	
/*	public void cargarOrdenesServicio() throws IOException{
		List<Ordenservicio> ordenes=new ArrayList<Ordenservicio>();
		int proyectoId=usuarioFinzytMB.getProyecto();
		ordenes=this.getOrdenservicioService().getAll(proyectoId);
		Collections.sort(ordenes,comparator);
		if(ordenes!=null && ordenes.size()>0){
			this.habSel.setCantOSsConsult(ordenes.size());
		}
		this.setOrdenes(ordenes);		
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("./listaOSAlt.jsf");
	}*/
	public String cargarOrdenesServicio(){
		List<Ordenservicio> ordenes=new ArrayList<Ordenservicio>();
		int proyectoId=usuarioFinzytMB.getProyecto();
		ordenes=this.getOrdenservicioService().getAll(proyectoId);
		Collections.sort(ordenes,comparator);
		if(ordenes!=null && ordenes.size()>0){
			this.habSel.setCantOSsConsult(ordenes.size());
		}
		this.setOrdenes(ordenes);		
		return "listadoOS";
	}
	
	public void cargarOrdenServicio(ActionEvent evento){
		UIParameter dato = (UIParameter) evento.getComponent().findComponent("idOS");
		String numeroDeOS = dato.getValue().toString();		
		OrdenservicioId id=new OrdenservicioId(numeroDeOS,usuarioFinzytMB.getProyecto());
		ordenDeservicio = this.getOrdenservicioService().findById(id);		
	}
	
	public void cargarDetalles(){
		
		List<Detalleos> detalles=new ArrayList<Detalleos>();		
		
		if(this.ordenDeservicio.getDetalleoses()!=null && this.ordenDeservicio.getDetalleoses().size()>0)
			detalles.addAll(this.ordenDeservicio.getDetalleoses());
		else System.out.print("Esta orden no tiene detalles Da Fuck!?");
		this.setDetalles(detalles);		
	}
	public String generarNro(){
		try {
			String numeroPreimpreso="";
			numeroPreimpreso=this.getOrdenservicioService().obtenerNroSiguiente(usuarioFinzytMB.getProyecto());
			this.ordenservicio.setOspreimpresoini(numeroPreimpreso);		
			System.out.println("El proyecto es:"+usuarioFinzytMB.getProyecto());
			return  numeroPreimpreso;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	} 
	public String obtenerFechaOS(){
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String fecha;
		Date date;
		int anio,mes,dia;
		anio=Calendar.getInstance().get(Calendar.YEAR);
		mes=Calendar.getInstance().get(Calendar.MONTH)+1;
		dia=Calendar.getInstance().get(Calendar.DATE);
		
		String a="",m=""+mes,d=""+dia;
		
		if(dia<10){
			d="";
			d="0"+dia;
		}
		if(mes<10){
			m="";
			m="0"+mes;
		}
		a=""+anio;
		fecha=dia+"/"+mes+"/"+anio;		
		
		try {
			date = formatter.parse(fecha);
			this.ordenservicio.setFechaos(date);
			fecha=d+"/"+m+"/"+a;
			return fecha;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
	}		
	public BigDecimal[] obtenerMontoTotal(List<Detalleos> detalles){
		BigDecimal total= new BigDecimal("0.0");		
		BigDecimal igvtotal= new BigDecimal("0.0");
		BigDecimal subtotal,igv;
		BigDecimal[] totales=new BigDecimal[2];
		for(Detalleos detalle:detalles){
			if(detalle!=null){
			subtotal=new BigDecimal("0.0");
			subtotal=detalle.getSubtotal();
			total=subtotal.add(total);
			igv=detalle.getIgv();
			igvtotal=igv.add(igvtotal);
			}else{break;}
		}		
		totales[0]=igvtotal;
		totales[1]=total;
		return totales;
	}
	public int getCantidadDetOS(){
		if(detallesos !=null){
			return this.detallesos.size();
		} else {
			return 0;
		}
		
	}
	
	public BigDecimal redondearIGV(BigDecimal igv){
		
		BigDecimal redondeado =igv.setScale(2, BigDecimal.ROUND_HALF_UP);
		System.out.println(redondeado);
		return redondeado;
	}
	
	
	public BigDecimal obtenerMonto(){
		BigDecimal subtotal = new BigDecimal(0.0);
		if( this.detalleos.getPreciounit()!=null){
			BigDecimal unit = this.detalleos.getPreciounit();
			this.detalleos.setSubtotal(unit);
		}
		return subtotal;
	}
	
	public BigDecimal agregarIGV(ValueChangeEvent event){
		BigDecimal MontoSinIGV = new BigDecimal("0.00");
		if(detalleos.getPreciounit()!=null){
			MontoSinIGV = this.detalleos.getPreciounit();
			Character valor = (Character)event.getNewValue();
			BigDecimal conIgvAdicional = new BigDecimal("1.18");
			BigDecimal igv = new BigDecimal("0.18");
			if(valor=='2'){
				BigDecimal auxMonto = redondearIGV(MontoSinIGV.multiply(conIgvAdicional));
				BigDecimal auxIGV = redondearIGV(MontoSinIGV.multiply(igv));
				this.detalleos.setIgv(auxIGV);
				this.detalleos.setSubtotal(auxMonto);
				return auxMonto;
			} else if(valor=='1'){
				BigDecimal aux = MontoSinIGV;
				BigDecimal auxMonto = redondearIGV(aux.divide(conIgvAdicional,9,RoundingMode.HALF_UP));
				BigDecimal auxIGV = redondearIGV(auxMonto.multiply(igv));
				MontoSinIGV = redondearIGV(MontoSinIGV);
				this.detalleos.setIgv(auxIGV);
				this.detalleos.setSubtotal(MontoSinIGV);
				return MontoSinIGV;
			}
		}
		return MontoSinIGV;
	}
	
	public String obtenerUltimoNro() {
		
		return null;
	}
	
	public String obtenerUltimoNroPreImpreso() {
		
		return null;
	}
/*	public void loadCommon() throws IOException{
		this.setMarcas(this.ordenservicioService.getAllMarcas());
		this.setUnidades(this.ordenservicioService.getAllUnidades());
		
		
		this.detalleos = new Detalleos();
		this.detallesos = new ArrayList<Detalleos>();
		this.cotizacionproveedor = new Cotizacionproveedor();
		this.ordenservicio = new Ordenservicio();
		this.articulos = new ArrayList<Articulo>();
		this.articulo = new Articulo();
		this.proveedor = new Proveedor();
		this.habSel = new HabilitadosySeleccionados();
		this.datoProveedor = "";
		this.descripcionArticulo="";
		
		generarNro();
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("./generarOS.jsf");
	//	return "generarOC";
	}*/
	public String loadCommon() {
		this.setMarcas(this.ordenservicioService.getAllMarcas());
		this.setUnidades(this.ordenservicioService.getAllUnidades());
		
		
		this.detalleos = new Detalleos();
		this.detallesos = new ArrayList<Detalleos>();
		this.cotizacionproveedor = new Cotizacionproveedor();
		this.ordenservicio = new Ordenservicio();
		this.articulos = new ArrayList<Articulo>();
		this.articulo = new Articulo();
		this.proveedor = new Proveedor();
		this.habSel = new HabilitadosySeleccionados();
		this.datoProveedor = "";
		this.descripcionArticulo="";
		
		generarNro();
		return "generarOS";
	}
/*	public void cargarOS(String osnro) throws IOException{
		int osestado = 0;
		Ordenservicio ordenR;
		OrdenservicioId id=new OrdenservicioId(osnro,usuarioFinzytMB.getProyecto());
			ordenR=this.ordenservicioService.findById(id);		
			this.setOrdenDeservicio(ordenR);
			if(ordenR.getOsestado().equals("1")){osestado=1;}else if(ordenR.getOsestado().equals("2")){osestado=2;}
			this.habSel.setOcestado(osestado);			
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./consultaOrdenDeServicio.jsf");	
	}*/
	
	public String cargarOS(String osnro,int opcion){
		
		Ordenservicio ordenR;
		OrdenservicioId id=new OrdenservicioId(osnro,usuarioFinzytMB.getProyecto());
			ordenR=this.ordenservicioService.findById(id);		
			this.setOrdenDeservicio(ordenR);			
			this.habSel.setOcestado(Metodos.
					convertirEstadoToInt(ordenR.
							getOsestado()));
			cargarDetalles();
			if(opcion==1){
				return "consultaOrdenDeServicio";
			}			
			else{
				return "editarOrdenServicio";
			}
			
	}
	public String actualizarOrdenServicio(){
		Ordenservicio paraActualizar=new Ordenservicio();
		List<Detalleos> detalless=new ArrayList<Detalleos>();
		boolean rpta;
		detalless=this.getDetalles();//Detalles de la vista de la orden de compra.
		//********** Actualizar Montos en OrdenCompra ********************
		BigDecimal[] montos = new BigDecimal[2];
		montos=obtenerMontoTotal(detalless);
		ordenDeservicio.setTotaligv(montos[0]);
		ordenDeservicio.setTotalos(montos[1]);
		paraActualizar=this.getOrdenDeservicio();
		rpta=this.getOrdenservicioService().
		updateOperacionesEditarOS(paraActualizar, detalless);
		if(rpta){
			log.info("Esta orden ha sido actualizada con exito =)! ");
				cargarOrdenesServicio();
			return "listadoOS";
		}else{
			log.info("Esto orden no se actualiz” ASDADSADSADFASDFA");
			return "actualizarOS";
		}
		
	}
	public String dirigir(){
		return "consultaOrdenDeServicio";
	}	
	
	public String registroProvBehavior(){
		ArrayList<Proveedor> proveedores;
		this.habSel.setBuscarProv(true);
		proveedores=(ArrayList<Proveedor>) mostrarProveedores();				
		if(proveedores!=null && proveedores.size()>0){			
			this.habSel.setProvtab("provtab1");
			this.habSel.setProveedores(proveedores);
		}else{			
			this.habSel.setProvtab("provtab2");
			this.habSel.setProveedores(null);
			
			this.setProveedor(new Proveedor());
		}
		return "generarOS";
	}
	public List<Proveedor> mostrarProveedores() {
		
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		Proveedor prmProveedor = new Proveedor();
		if (this.habSel.isBuscarProv()) {
			if (datoProveedor != null)
				datoProveedor.toUpperCase();
			if (this.habSel.getModoProv()==0) {
				prmProveedor.setProruc(datoProveedor);
				proveedores.addAll(this.ordenservicioService.findByProveedor(
						prmProveedor, 0));
			} else {
				prmProveedor.setProrazsoc(datoProveedor);
				proveedores.addAll(this.ordenservicioService.findByProveedor(
						prmProveedor, 1));
			}			
			this.habSel.setCantProveedores(proveedores.size());
		}else{
			this.habSel.setCantProveedores(4);
		}
		
		if(this.habSel.getCantProveedores()==0){
			this.habSel.setProvtab("provtab2");			
		}
		return proveedores;
	}
	
	public void grabarDetalleOS(Detalleos detalle){
		BigDecimal[] total=new BigDecimal[2];		
		Detalleos det = new Detalleos();
		det = detalle;
		this.detallesos.add(det);
		this.detalleos = new Detalleos();
		this.habSel.setArttab("arttab1");
		total=obtenerMontoTotal(detallesos);
		this.setMontoTotal(total[1]);
		
		
	}
	public void eliminarDetalle(Detalleos detalle){
		BigDecimal montoActual=new BigDecimal("0.0");
		montoActual=this.getMontoTotal().subtract(detalle.getSubtotal());
		this.detallesos.remove(detalle);
		this.setMontoTotal(montoActual);		
	}
	
	public String registroArtBehavior(){
		ArrayList<Articulo> articulos;
		this.habSel.setBuscarArt(true);
		articulos= (ArrayList<Articulo>)buscarArticulos();
		if(articulos!=null && articulos.size()>0){
			this.habSel.setArttab("arttab1");
			this.habSel.setArticulos(articulos);
		}else{
			this.habSel.setArttab("arttab2");
			this.habSel.setArticulos(null);
			this.setArticulo(new Articulo());
			this.setDetalleos(new Detalleos());
		}		
		return "generarOS";
	} 
	public List<Articulo> buscarArticulos() {
		
		List<Articulo> articulos=new ArrayList<Articulo>();
		Articulo prmArticulo = new Articulo();
		if(this.habSel.isBuscarArt()){
			if(descripcionArticulo!=null){
				prmArticulo.setArtdes(descripcionArticulo.toUpperCase());
				articulos.addAll(this.ordenservicioService.findByArticulo(prmArticulo));
				this.habSel.setCantArticulos(articulos.size());
			} else {
				this.habSel.setCantArticulos(4);
			}			
		}		

		if(this.habSel.getCantArticulos()==0){
			this.habSel.setArttab("arttab2");;
			
		}
		return articulos;
	}
	
	public String asignarProveedor(Proveedor proveedor){
		this.setProveedor(proveedor);
		this.habSel.setProvtab("provtab2");		
		return "generarOS";
	}
	
	public String registrarNuevoProveedor(){
		this.habSel.setProvtab("provtab1");
		this.setDatoProveedor("");
		return "";
	}
	
	public String registrarNuevoArticulo(){
		return "generarOS";
	}
	public String asignarArticulo(Articulo articulo){
		this.setArticulo(articulo);
		Detalleos detalleos=new Detalleos();
		detalleos.setArtcod(articulo.getArtcod());
		detalleos.setArtdes(articulo.getArtdes());
		this.setDetalleos(detalleos);
		this.habSel.setArttab("arttab2");
		return "generarOS";
	}
	//Metodo Ajax
	public List<String> buscarMarca(String begin){
		ArrayList<String> alResult = null;
		List<String> lEmpty=Arrays.asList(new String[0]);		
		Iterator iterator=null;
		String firstLetra=begin.trim();
		if(firstLetra.length()==0)
		{return lEmpty;}
		firstLetra.toLowerCase();
		alResult = new ArrayList<String>();		
		iterator=marcas.iterator();
		while(iterator.hasNext()){
			String sElem=(String)iterator.next();
			if(sElem.toLowerCase().startsWith(firstLetra))
			{alResult.add(sElem);}			
		}
		return alResult;		
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Ordenservicio getOrdenservicio() {
		return ordenservicio;
	}

	public void setOrdenservicio(Ordenservicio ordenservicio) {
		this.ordenservicio = ordenservicio;
	}

	public Cotizacionproveedor getCotizacionproveedor() {
		return cotizacionproveedor;
	}

	public void setCotizacionproveedor(Cotizacionproveedor cotizacionproveedor) {
		this.cotizacionproveedor = cotizacionproveedor;
	}

	public List<Detalleos> getDetallesos() {
		return detallesos;
	}

	public void setDetallesos(List<Detalleos> detallesos) {
		this.detallesos = detallesos;
	}

	public Detalleos getDetalleos() {
		return detalleos;
	}

	public void setDetalleos(Detalleos detalleos) {
		this.detalleos = detalleos;
	}

	public ArrayList<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(ArrayList<Articulo> articulos) {
		this.articulos = articulos;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public HabilitadosySeleccionados getHabSel() {
		return habSel;
	}

	public void setHabSel(HabilitadosySeleccionados habSel) {
		this.habSel = habSel;
	}

	public List<String> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<String> marcas) {
		this.marcas = marcas;
	}

	public List<String> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<String> unidades) {
		this.unidades = unidades;
	}

	public int getSeleccion() {
		return seleccion;
	}

	public void setSeleccion(int seleccion) {
		this.seleccion = seleccion;
	}

	public String getDatoProveedor() {
		return datoProveedor;
	}

	public void setDatoProveedor(String datoProveedor) {
		this.datoProveedor = datoProveedor;
	}

	public String getDescripcionArticulo() {
		return descripcionArticulo;
	}

	public void setDescripcionArticulo(String descripcionArticulo) {
		this.descripcionArticulo = descripcionArticulo;
	}

	public List<Ordenservicio> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(List<Ordenservicio> ordenes) {
		this.ordenes = ordenes;
	}

	public Ordenservicio getOrdenDeservicio() {
		return ordenDeservicio;
	}

	public void setOrdenDeservicio(Ordenservicio ordenDeservicio) {
		this.ordenDeservicio = ordenDeservicio;
	}

	public OrdenFiltroMB getoSFiltroMB() {
		return oSFiltroMB;
	}

	public void setoSFiltroMB(OrdenFiltroMB oSFiltroMB) {
		this.oSFiltroMB = oSFiltroMB;
	}
	public OrdenservicioService getOrdenservicioService() {
		return ordenservicioService;
	}

	public void setOrdenservicioService(OrdenservicioService ordenservicioService) {
		this.ordenservicioService = ordenservicioService;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	public List<Detalleos> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Detalleos> detalles) {
		this.detalles = detalles;
	}
	
	
	
	
}
