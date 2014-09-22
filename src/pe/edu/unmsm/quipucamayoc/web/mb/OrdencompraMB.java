package pe.edu.unmsm.quipucamayoc.web.mb;

import java.io.IOException;
import java.io.Serializable;
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
import javax.faces.context.FacesContext;
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
import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Proveedor;
import pe.edu.unmsm.quipucamayoc.service.inf.OrdencompraService;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HabilitadosySeleccionados;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.Metodos;

@Controller
@Scope("session")
public class OrdencompraMB {	
	
	@Autowired
	private OrdencompraService ordencompraService;		
	private Ordencompra ordencompra;	
	private Proveedor proveedor;	
	private Cotizacionproveedor cotizacionproveedor;	
	private List<Detalleoc> detallesoc;
	private Detalleoc detalleoc;
	private ArrayList<Articulo> articulos;	
	private Articulo articulo;		
	private HabilitadosySeleccionados habSel;	
	private List<String> marcas;
	private List<String> unidades;
	private int seleccion;// Seleccion de proveedor
	
	private String datoProveedor="";// dato a buscar proveedor
	private String descripcionArticulo="";// descripcion de articulo
	
	private BigDecimal montoTotal;
	
	private static String USER_NAME = "quipucamayoc@unmsm.edu.pe";
	private static String PASSWORD = "1234qwer";
	/**
	 * En el caso de las consultas:
	 */
	private List<Ordencompra> ordenes;
	private Ordencompra ordenDecompra;//OC seleccionada y visualizada con la Lupa:
	private List<Detalleoc> detalles;
	
	@Autowired
	private UsuarioFinzytMB usuarioFinzytMB;
	
	private OrdenFiltroMB oCFiltroMB;
	
	private Log log=LogFactory.getLog(UsuarioFinzytMB.class);
	public OrdencompraMB() {
		super();
		this.init();		
	}
	
	private void init() {		
		this.setOrdencompra(new Ordencompra());		
		this.setCotizacionproveedor(new Cotizacionproveedor());
		this.setDetallesoc(new ArrayList<Detalleoc>());
		this.setDetalleoc(new Detalleoc());
		this.setProveedor(new Proveedor());
		this.setArticulo(new Articulo());
		this.setMarcas(new ArrayList<String>());
		this.setHabSel(new HabilitadosySeleccionados());
		this.setOrdenDecompra(new Ordencompra());
		this.setOrdenes(new ArrayList<Ordencompra>());
		this.setMontoTotal(new BigDecimal("0.0"));
		log.info("Entrando al constructor px!");
		log.info("Entrando al constructor 2 veces px!");
	}	

	public OrdencompraMB(Ordencompra ordencompra) {
		super();
		this.ordencompra = ordencompra;
	}
	
	public void registrarOrdencompra() {
		/**
		 * @param estado:	0:Generado
		 * 					1:Enviado
		 * 					2:Verificado 					
		 */
		String estado;
		System.out.print("proyecto llega: "+usuarioFinzytMB.getProyecto());
		Ordencompra ordencompra=new Ordencompra();
		List<Detalleoc> detalles=new ArrayList<Detalleoc>();		
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
		/*Fecha para el registro de la Orden de compra*/

		//Totales IGV Y Orden Compra
		Object[] totales=new Object[2];
		totales=obtenerMontoTotal(detallesoc);
		
		estado="1";
		ordencompra.setReferencia(this.ordencompra.getReferencia());//Tambien conocido como expediente
		ordencompra.setSolicitante("Proyecto N∫: "+ habSel.FormatFINCyt().get(usuarioFinzytMB.getProyecto()));//Por mientras
		
		ordencompra.setId(new OrdencompraId("",usuarioFinzytMB.getProyecto()));
		
		if(obtenerFechaOC()!=null){
		Date fechaoc=null;
		SimpleDateFormat sample=new SimpleDateFormat("dd/MM/yyyy");
		try {
			fechaoc=sample.parse(obtenerFechaOC());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		ordencompra.setFechaoc(fechaoc);
		}
		else{
		ordencompra.setFechaoc(new Date());
		}
		ordencompra.setFechaimp(new Date());//Cuando imprima la Orden de compra
		ordencompra.setTotaligv((BigDecimal)totales[0]);
		ordencompra.setTotaloc((BigDecimal)totales[1]);		
		ordencompra.setAlmdir("ALMACEN DEL FINCYT");//ASADASDASDF
		ordencompra.setObservacion(this.ordencompra.getObservacion());//Falta aun el campo
		ordencompra.setOcestado(estado);
		ordencompra.setOcpreimpresoini(this.ordencompra.getOcpreimpresoini());//Lado Servidor
		ordencompra.setCotizacionproveedor(cotProv);
		
		detalles.addAll(this.getDetallesoc());
		
		boolean enviarEmail =this.getOrdencompraService().save(ordencompra, detalles);
		if(enviarEmail){
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CAP")){
				rol="CGI";
			}
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="NUEVA ORDEN DE COMPRA - FINZYT";
			String body="Se ha enviado la Orden de Compra N∞ "+this.ordencompra.getOcpreimpresoini()+" para ser validada por Ud.";
		//	sendFromEmail(USER_NAME,PASSWORD,to,subject,body);
		}
		this.detalleoc = new Detalleoc();
		this.detallesoc = new ArrayList<Detalleoc>();
		this.cotizacionproveedor = new Cotizacionproveedor();
		this.ordencompra = new Ordencompra();
		this.articulos = new ArrayList<Articulo>();
		this.articulo = new Articulo();
		this.proveedor = new Proveedor();
		this.habSel = new HabilitadosySeleccionados();
		this.datoProveedor = "";
		this.descripcionArticulo="";
		this.setMontoTotal(new BigDecimal("0.0"));
		generarNro();
		
	}
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
	
/*	public void validarOrdenCompra() throws IOException{		
		Ordencompra oc=new Ordencompra();
		
		oc=this.ordenDecompra;//OC seleccionada.
		oc.setOcestado("2");
		boolean rpta=this.getOrdencompraService().validar(oc);
		if(rpta){
			this.habSel.setOcestado(2);
			cargarOrdenesCompra();
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CGI")){
				rol="CAP";
			}
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="LA ORDEN DE COMPRA "+oc.getOcpreimpresoini()+" SE HA VALIDADO";
			String body="Se ha validado la Orden de Compra "+oc.getOcpreimpresoini()+", ya puede imprimirla.";
			sendFromEmail(USER_NAME,PASSWORD,to,subject,body);

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./listaOCAlt.jsf");
		}else{			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./consultaOrdenDeCompra.jsf");		
		}
	}*/
	
	public String validarOrdenCompra(){		
		Ordencompra oc=new Ordencompra();
		
		oc=this.ordenDecompra;//OC seleccionada.
		oc.setOcestado("2");
		boolean rpta=this.getOrdencompraService().validar(oc);
		if(rpta){
			this.habSel.setOcestado(2);
			cargarOrdenesCompra();
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CGI")){
				rol="CAP";
			}
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="LA ORDEN DE COMPRA "+oc.getOcpreimpresoini()+" SE HA VALIDADO";
			String body="Se ha validado la Orden de Compra "+oc.getOcpreimpresoini()+", ya puede imprimirla.";
			//sendFromEmail(USER_NAME,PASSWORD,to,subject,body);

			return "listadoOC";
		}else{			
			return "consultaOrdenDeCompra";
		}
	}

	public String editarOrdenCompra(String ocnro){
		Ordencompra orden;
		OrdencompraId id=new OrdencompraId(ocnro,usuarioFinzytMB.getProyecto());
		orden=this.ordencompraService.findById(id);
		this.setOrdencompra(orden);
		this.habSel.setOcestado(Metodos.
				convertirEstadoToInt(orden.
						getOcestado()));		
		return "editarOrdenCompra";
	}
	Comparator<Ordencompra> comparator=new Comparator<Ordencompra>(){
		@Override
		public int compare(Ordencompra a, Ordencompra b) {
			// TODO Auto-generated method stub
			Long numa=Long.parseLong(a.getId().getOcnro());
			Long numb=Long.parseLong(b.getId().getOcnro());
			return (int)(numa-numb);
		}
	};
/*	public void cargarOrdenesCompra() throws IOException{
		List<Ordencompra> ordenes=new ArrayList<Ordencompra>();
		int proyectoId=usuarioFinzytMB.getProyecto();
		log.info("Usuario Control : "+this);
		ordenes=this.getOrdencompraService().getAll(proyectoId);
		Collections.sort(ordenes,comparator);
		if(ordenes!=null && ordenes.size()>0){
			this.habSel.setCantOCsConsult(ordenes.size());
		}
		this.setOrdenes(ordenes);		
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("./listaOCAlt.jsf");
	}*/
	
	
	public String cargarOrdenesCompra(){
		List<Ordencompra> ordenes=new ArrayList<Ordencompra>();
		int proyectoId=usuarioFinzytMB.getProyecto();
		log.info("Usuario Control : "+this);
		ordenes=this.getOrdencompraService().getAll(proyectoId);
		Collections.sort(ordenes,comparator);
		if(ordenes!=null && ordenes.size()>0){
			this.habSel.setCantOCsConsult(ordenes.size());
		}
		this.setOrdenes(ordenes);		
		return "listadoOC";
	}
	public void cargarOrdenCompra(ActionEvent evento){
		UIParameter dato = (UIParameter) evento.getComponent().findComponent("idOC");
		String numeroDeOC = dato.getValue().toString();		
		OrdencompraId id=new OrdencompraId(numeroDeOC,usuarioFinzytMB.getProyecto());
		ordenDecompra = this.getOrdencompraService().findById(id);		
	}
	
	private void cargarDetalles(){
		
		detalles=new ArrayList<Detalleoc>();		
		
		if(this.ordenDecompra.getDetalleocs()!=null && this.ordenDecompra.getDetalleocs().size()>0)
			detalles.addAll(this.ordenDecompra.getDetalleocs());
		else System.out.print("Esta orden no tiene detalles Da Fuck!?");		
		this.setDetalles(detalles);
	}
	public String generarNro(){
		try {
			String numeroPreimpreso="";
			numeroPreimpreso=this.getOrdencompraService().obtenerNroSiguiente(usuarioFinzytMB.getProyecto());
			this.ordencompra.setOcpreimpresoini(numeroPreimpreso);		
			System.out.println("El proyecto es:"+usuarioFinzytMB.getProyecto());
			return  numeroPreimpreso;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public String obtenerFechaOC(){
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
			this.ordencompra.setFechaoc(date);
			fecha=d+"/"+m+"/"+a;
			return fecha;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
	}		
	public BigDecimal[] obtenerMontoTotal(List<Detalleoc> detalles){
		BigDecimal total= new BigDecimal("0.0");
		BigDecimal igvtotal= new BigDecimal("0.0");
		BigDecimal subtotal,igv;
		BigDecimal[] totales=new BigDecimal[2];		
		for(Detalleoc detalle:detalles){
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
	public int getCantidadDetOC(){
		if(detallesoc !=null){
			return this.detallesoc.size();
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
		if( this.detalleoc.getPreciounit()!=null && this.detalleoc.getCantidad()!=null){
			BigDecimal unit = this.detalleoc.getPreciounit();
			BigDecimal cant = this.detalleoc.getCantidad();
			subtotal = unit.multiply(cant);
			this.detalleoc.setSubtotal(subtotal);
		}
		return subtotal;
	}
	
	public BigDecimal agregarIGV(ValueChangeEvent event){
		BigDecimal MontoSinIGV = new BigDecimal("0.00");
		if(detalleoc.getCantidad()!=null&&detalleoc.getPreciounit()!=null){
			MontoSinIGV = this.detalleoc.getCantidad().multiply(this.detalleoc.getPreciounit());
			Character valor = (Character)event.getNewValue();
			BigDecimal conIgvAdicional = new BigDecimal("1.18");
			BigDecimal igv = new BigDecimal("0.18");
			if(valor=='2'){//NO INCLUYE IGV
				BigDecimal auxMonto = redondearIGV(MontoSinIGV.multiply(conIgvAdicional));
				BigDecimal auxIGV = redondearIGV(MontoSinIGV.multiply(igv));
				this.detalleoc.setIgv(auxIGV);
				this.detalleoc.setSubtotal(auxMonto);
				return auxMonto;
			} else if(valor=='1'){//INCLUYE IGV
				BigDecimal aux = MontoSinIGV;
				BigDecimal auxMonto = redondearIGV(aux.divide(conIgvAdicional,9,RoundingMode.HALF_UP));
				BigDecimal auxIGV = redondearIGV(auxMonto.multiply(igv));
				MontoSinIGV = redondearIGV(MontoSinIGV);
				this.detalleoc.setIgv(auxIGV);
				this.detalleoc.setSubtotal(MontoSinIGV);
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
		this.setMarcas(this.ordencompraService.getAllMarcas());
		this.setUnidades(this.ordencompraService.getAllUnidades());
		
		
		this.detalleoc = new Detalleoc();
		this.detallesoc = new ArrayList<Detalleoc>();
		this.cotizacionproveedor = new Cotizacionproveedor();
		this.ordencompra = new Ordencompra();
		this.articulos = new ArrayList<Articulo>();
		this.articulo = new Articulo();
		this.proveedor = new Proveedor();
		this.habSel = new HabilitadosySeleccionados();
		this.datoProveedor = "";
		this.descripcionArticulo="";
		
		generarNro();
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("./generarOC.jsf");
	//	return "generarOC";
		System.out.print(this);
	}*/
	public String loadCommon(){
		this.setMarcas(this.ordencompraService.getAllMarcas());
		this.setUnidades(this.ordencompraService.getAllUnidades());
		
		
		this.detalleoc = new Detalleoc();
		this.detallesoc = new ArrayList<Detalleoc>();
		this.cotizacionproveedor = new Cotizacionproveedor();
		this.ordencompra = new Ordencompra();
		this.articulos = new ArrayList<Articulo>();
		this.articulo = new Articulo();
		this.proveedor = new Proveedor();
		this.habSel = new HabilitadosySeleccionados();
		this.datoProveedor = "";
		this.descripcionArticulo="";
		
		generarNro();

		return "generarOC";

	}	

/*	public void cargarOC(String ocnro) throws IOException{
		int ocestado = 0;
		Ordencompra ordenR;
		OrdencompraId id=new OrdencompraId(ocnro,usuarioFinzytMB.getProyecto());
			ordenR=this.ordencompraService.findById(id);		
			this.setOrdenDecompra(ordenR);
			if(ordenR.getOcestado().equals("1")){ocestado=1;}else if(ordenR.getOcestado().equals("2")){ocestado=2;}
			this.habSel.setOcestado(ocestado);			
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./consultaOrdenDeCompra.jsf");	
	}	*/
	
	public String cargarOC(String ocnro,int opcion) {		
		Ordencompra ordenR;
		OrdencompraId id=new OrdencompraId(ocnro,usuarioFinzytMB.getProyecto());
			ordenR=this.ordencompraService.findById(id);		
			this.setOrdenDecompra(ordenR);			
			this.habSel.setOcestado(Metodos.
					convertirEstadoToInt(ordenR.getOcestado()));
			cargarDetalles();
			if(opcion==1)
			return "consultaOrdenDeCompra";
			else
			return "editarOrdenCompra";
	}
	public String actualizarOrdenCompra(){
		Ordencompra paraActualizar=new Ordencompra();
		List<Detalleoc> detalless=new ArrayList<Detalleoc>();
		boolean rpta;
		detalless=this.getDetalles();//Detalles de la vista de la orden de compra.
		//********** Actualizar Montos en OrdenCompra ********************
		BigDecimal[] montos = new BigDecimal[2];
		montos=obtenerMontoTotal(detalless);
		ordenDecompra.setTotaligv(montos[0]);
		ordenDecompra.setTotaloc(montos[1]);
		paraActualizar=this.getOrdenDecompra();
		
		
		System.out.println(detalles.get(0).getImpuesto());
		System.out.println(detalles.get(1).getImpuesto());
		
		rpta=this.getOrdencompraService().
		updateOperacionesEditarOC(paraActualizar, detalless);
		if(rpta){
			log.info("Esta orden ha sido actualizada con exito =)! ");
			cargarOrdenesCompra();
			return "listadoOC";
		}else{
			log.info("Esto orden no se actualiz” ASDADSADSADFASDFA");
			return "actualizarOC";
		}
		
	}
	public String registroProvBehavior(){
		ArrayList<Proveedor> proveedores;
		this.habSel.setBuscarProv(true);
		log.info("Hola que aze!!!!!");
		log.info("Si se puede!!");
		proveedores=(ArrayList<Proveedor>) mostrarProveedores();				
		if(proveedores!=null && proveedores.size()>0){			
			this.habSel.setProvtab("provtab1");
			this.habSel.setProveedores(proveedores);			
		}else{			
			this.habSel.setProvtab("provtab2");
			this.habSel.setProveedores(null);			
			this.setProveedor(new Proveedor());
		}		
			
		return "generarOC";
	}
	public List<Proveedor> mostrarProveedores() {
		System.out.print("RUC" + datoProveedor);
		System.out.print("RUC" + seleccion);
		List<Proveedor> proveedores=new ArrayList<Proveedor>();
		Proveedor prmProveedor = new Proveedor();
		if (this.habSel.isBuscarProv()) {
			if (datoProveedor != null)
				datoProveedor.toUpperCase();
			if (this.habSel.getModoProv()==0) {
				prmProveedor.setProruc(datoProveedor);
				proveedores.addAll(this.ordencompraService.findByProveedor(
						prmProveedor, 0));
			} else {
				prmProveedor.setProrazsoc(datoProveedor);
				proveedores.addAll(this.ordencompraService.findByProveedor(
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
	
	public void grabarDetalleOC(Detalleoc detalle){
		BigDecimal[] total=new BigDecimal[2];
		Detalleoc det = new Detalleoc();
		det = detalle;
		this.detallesoc.add(det);
		this.detalleoc = new Detalleoc();
		this.habSel.setArttab("arttab1");
		//Obtiene el monto hasta
		total=obtenerMontoTotal(detallesoc);		
		this.setMontoTotal(total[1]);
	}
	public void eliminarDetalle(Detalleoc detalle){
		BigDecimal montoActual=new BigDecimal("0.0");
		montoActual=this.getMontoTotal().subtract(detalle.getSubtotal());
		this.detallesoc.remove(detalle);
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
			this.setDetalleoc(new Detalleoc());
		}		
		return "generarOC";
	} 
	public List<Articulo> buscarArticulos() {
		
		List<Articulo> articulos=new ArrayList<Articulo>();
		Articulo prmArticulo = new Articulo();
		if(this.habSel.isBuscarArt()){
			if(descripcionArticulo!=null){
				prmArticulo.setArtdes(descripcionArticulo.toUpperCase());
				articulos.addAll(this.ordencompraService.findByArticulo(prmArticulo));
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
		return "generarOC";
	}
	
	public String registrarNuevoProveedor(){		
		this.habSel.setProvtab("provtab1");
		this.setDatoProveedor("");
		return "";
	}
	
	public String registrarNuevoArticulo(){
		return "generarOC";
	}
	public String asignarArticulo(Articulo articulo){
		this.setArticulo(articulo);
		Detalleoc detalleoc=new Detalleoc();
		detalleoc.setArtcod(articulo.getArtcod());
		detalleoc.setArtdes(articulo.getArtdes());
		this.setDetalleoc(detalleoc);
		this.habSel.setArttab("arttab2");
		return "generarOC";
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
	public String registrarCondiciones() {

		return null;
	}	

	// Supuestamente con ajax
	public String listarDetallesOC() {// Entra a tallar el resumen de la OC

		return null;
	}

	public Ordencompra getOrdencompra() {
		return ordencompra;
	}

	public void setOrdencompra(Ordencompra ordencompra) {
		this.ordencompra = ordencompra;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	public ArrayList<Articulo> getArticulos() {
		return articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = (ArrayList<Articulo>) articulos;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
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
	
	public Cotizacionproveedor getCotizacionproveedor() {
		return cotizacionproveedor;
	}

	public void setCotizacionproveedor(Cotizacionproveedor cotizacionproveedor) {
		this.cotizacionproveedor = cotizacionproveedor;
	}

	public List<Detalleoc> getDetallesoc() {
		return detallesoc;
	}

	public void setDetallesoc(List<Detalleoc> detallesoc) {
		this.detallesoc = detallesoc;
	}

	public Detalleoc getDetalleoc() {
		return detalleoc;

	}
	public void setDetalleoc(Detalleoc detalleoc) {
		this.detalleoc = detalleoc;
	}

	public List<String> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<String> marcas) {
		this.marcas=marcas;
	}

	public HabilitadosySeleccionados getHabSel() {
		return habSel;
	}

	public void setHabSel(HabilitadosySeleccionados habSel) {
		this.habSel = habSel;
	}

	public OrdencompraService getOrdencompraService() {
		return ordencompraService;
	}

	public void setOrdencompraService(OrdencompraService ordencompraService) {
		this.ordencompraService = ordencompraService;
	}

	public Ordencompra getOrdenDecompra() {
		return ordenDecompra;
	}

	public void setOrdenDecompra(Ordencompra ordenDecompra) {
		this.ordenDecompra = ordenDecompra;
	}

	public List<Ordencompra> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(List<Ordencompra> ordenes) {
		this.ordenes = ordenes;
	}

	public OrdenFiltroMB getoCFiltroMB() {
		return oCFiltroMB;
	}

	public void setoCFiltroMB(OrdenFiltroMB oCFiltroMB) {
		this.oCFiltroMB = oCFiltroMB;
	}

	public List<String> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<String> unidades) {
		this.unidades = unidades;
	}

	public List<Detalleoc> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Detalleoc> detalles) {
		this.detalles = detalles;
	}

	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}
}
