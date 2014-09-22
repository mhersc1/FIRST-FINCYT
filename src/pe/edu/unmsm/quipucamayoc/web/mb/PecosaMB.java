package pe.edu.unmsm.quipucamayoc.web.mb;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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

import pe.edu.unmsm.quipucamayoc.model.Detalleoc;
import pe.edu.unmsm.quipucamayoc.model.Ordencompra;
import pe.edu.unmsm.quipucamayoc.model.OrdencompraId;
import pe.edu.unmsm.quipucamayoc.model.Pecosa;
import pe.edu.unmsm.quipucamayoc.model.PecosaId;
import pe.edu.unmsm.quipucamayoc.service.inf.OrdencompraService;
import pe.edu.unmsm.quipucamayoc.service.inf.PecosaService;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.HabilitadosySeleccionados;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.Metodos;

@Controller
@Scope("session")
public class PecosaMB {	

	@Autowired
	private PecosaService pecosaService;
	@Autowired
	private OrdencompraMB ordencompraMB;
	@Autowired
	private UsuarioFinzytMB usuarioFinzytMB;
	@Autowired
	private OrdencompraService ordencompraService;
	private List<Pecosa> pecosas;
	private Pecosa pecosa;
	private Ordencompra ordencompra;
	private HabilitadosySeleccionados habSel;
	
	private static String USER_NAME = "quipucamayoc@unmsm.edu.pe";
	private static String PASSWORD = "1234qwer";
	
	private Log log=LogFactory.getLog(PecosaMB.class);
	
	public PecosaMB() {
	// TODO Auto-generated constructor stub
	this.setPecosa(new Pecosa());
	this.setOrdencompra(new Ordencompra());
	this.setHabSel(new HabilitadosySeleccionados());
	this.setPecosas(new ArrayList<Pecosa>());
	habSel=new HabilitadosySeleccionados();
	}
	
	Comparator<Pecosa> comparator=new Comparator<Pecosa>(){
		@Override
		public int compare(Pecosa a, Pecosa b) {
			// TODO Auto-generated method stub
			Long numa=a.getId().getPecnro();
			Long numb=b.getId().getPecnro();
			return (int)(numa-numb);
		}
	};
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
			this.pecosa.setFechapec(date);
			fecha=d+"/"+m+"/"+a;
			return fecha;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
		
	}	
/*	public void cargarPecosas() throws IOException{
		List<Pecosa> pecosas=new ArrayList<Pecosa>();
		int proyectoId=usuarioFinzytMB.getProyecto();
		pecosas=this.getPecosaService().getAll(proyectoId);
		Collections.sort(pecosas,comparator);
		if(pecosas !=null && pecosas.size()>0){
			this.habSel.setCantPecosasConsult(pecosas.size());
		}
		this.setPecosas(pecosas);
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("./listaPECOSAlt.jsf");
	}*/
	public String cargarPecosas(){
		List<Pecosa> pecosas=new ArrayList<Pecosa>();
		int proyectoId=usuarioFinzytMB.getProyecto();
		pecosas=this.getPecosaService().getAll(proyectoId);
		Collections.sort(pecosas,comparator);
		if(pecosas !=null && pecosas.size()>0){
			this.habSel.setCantPecosasConsult(pecosas.size());
		}
		this.setPecosas(pecosas);
		return "listadoPECOSA";
	}

	public String cargarOCparaAlm(String ocnro) {
		String nroPecosa;
		Ordencompra ordenR;
		Pecosa pecosa=new Pecosa();
		OrdencompraId id=new OrdencompraId(ocnro,usuarioFinzytMB.getProyecto());
		ordenR=this.ordencompraService.findById(id);
		this.setOrdencompra(ordenR);
		nroPecosa=this.pecosaService.obtenerNroSiguiente(id.getProyectoid());		
		this.pecosa.setPreimpresoinicial(nroPecosa);
		return "generarPecosa";
	}
	
	public void cargarOC(String ocnro){
		Ordencompra ordenR;
		OrdencompraId id = new OrdencompraId(ocnro,usuarioFinzytMB.getProyecto());
		ordenR = this.ordencompraService.findById(id);
		this.setOrdencompra(ordenR);
	}
	
	public String actualizarPecosa(){
		Pecosa pecosaPorActualizar=new Pecosa();
		pecosaPorActualizar.setId(new PecosaId(this.pecosa.getId().getPecnro(),
				this.pecosa.getId().getProyectoid()));
		pecosaPorActualizar.setPecdes(this.pecosa.getPecdes());
		pecosaPorActualizar.setPecdesdir(this.pecosa.getPecdesdir());
		pecosaPorActualizar.setPecobs(this.pecosa.getPecobs());
		Ordencompra ocPorActualizar=new Ordencompra();
		ocPorActualizar.setId(new OrdencompraId(this.ordencompra.getId().getOcnro(),
				this.ordencompra.getId().getProyectoid()));
		/**
		 * Campos para actualizar en Pecosa.
		 */
		ocPorActualizar.setReferencia(this.ordencompra.getReferencia());
		ocPorActualizar.setFactura(this.ordencompra.getFactura());
		ocPorActualizar.setGuiaDeRemision(this.ordencompra.getGuiaDeRemision());
		
		if(this.getPecosaService().updateOperacionesEditarPecosa(pecosaPorActualizar,ocPorActualizar)){
			cargarPecosas();
			return "listadoPecosa";
		}else{
			return "actualizarPecosa";
		}	
	}
	
	public void dirigir(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/SYSFINZYTV/pages/common/accessdenied.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String registrarPecosa(){
	Ordencompra orden=new Ordencompra();
	String pecestado="1";
	String ocestado="3";
	this.pecosa.setPecestado(pecestado);
	OrdencompraId id=new OrdencompraId(this.ordencompra.getId().getOcnro(),usuarioFinzytMB.getProyecto());
	//Se obtiene la orden de compra.
	orden = this.getOrdencompraService().findById(id);
	//Los campos nuevos
	orden.setFactura(this.ordencompra.getFactura());
	orden.setGuiaDeRemision(this.ordencompra.getGuiaDeRemision());
	orden.setOcestado(ocestado);
	/////////////////////
	//Se salva la pecosa
	if(!this.getPecosaService().save(pecosa, orden)){
		log.info("No se pudo generar la pecosa");			
	return "generarPecosa";
	}else{
		ordencompraMB.cargarOrdenesCompra();//lelelel :v
		return "listadoOC";
	}
	
	}

	
/*	public void cargarPecosa(Long pecnro) throws IOException{
		int pecestado=0;
		Pecosa pecosa;
		PecosaId pecId=new PecosaId(pecnro,usuarioFinzytMB.getProyecto());
		pecosa=this.pecosaService.findById(pecId);
		cargarOC(pecosa.getOcnro());
		this.setPecosa(pecosa);
		if(pecosa.getPecestado().equals("1")){pecestado=1;}else 
		if(pecosa.getPecestado().equals("2")){pecestado=2;}
		this.habSel.setPecestado(pecestado);
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("./consultaPecosa.jsf");
	}	*/
	public String cargarPecosa(Long pecnro,int opcion){
		int pecestado=0;
		Pecosa pecosa;
		PecosaId pecId=new PecosaId(pecnro,usuarioFinzytMB.getProyecto());
		pecosa=this.pecosaService.findById(pecId);
		cargarOC(pecosa.getOcnro());
		this.setPecosa(pecosa);
		pecestado=Metodos.convertirEstadoToInt(pecosa.getPecestado());
		this.habSel.setPecestado(pecestado);
		if(opcion==1){
			return "consultaPecosa";
		}else{
			return "editarPecosa";
		}
		
	}	
	
/*	public void validarPecosa() throws IOException{		
		Pecosa pecosa=new Pecosa();
		Ordencompra orden = new Ordencompra();
		pecosa=this.pecosa;//OC seleccionada.
		orden = this.ordencompra;
		pecosa.setPecestado("2");
		orden.setOcestado("4");
		boolean rpta=this.getPecosaService().validar(pecosa,orden);
		if(rpta){
			this.ordencompra.setOcestado("4");
			this.pecosa.setPecestado("2");
			cargarPecosas();
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CGI")){
				rol="CAP";
			}
			
			
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="LA PECOSA "+pecosa.getPreimpresoinicial()+" SE HA VALIDADO";
			String body="Se ha validado la Pecosa "+pecosa.getPreimpresoinicial()+", ya puede imprimirla.";
			sendFromEmail(USER_NAME,PASSWORD,to,subject,body);

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./consultaPecosa.jsf");
		}else{			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("./consultaPecosa.jsf");		
		}
	}*/
	
	public String validarPecosa(){		
		Pecosa pecosa=new Pecosa();
		Ordencompra orden = new Ordencompra();
		pecosa=this.pecosa;//OC seleccionada.
		orden = this.ordencompra;
		pecosa.setPecestado("2");
		orden.setOcestado("4");
		boolean rpta=this.getPecosaService().validar(pecosa,orden);
		if(rpta){
			this.ordencompra.setOcestado("4");
			this.pecosa.setPecestado("2");
			cargarPecosas();
			String rol = "";
			if(usuarioFinzytMB.getRol().equals("CGI")){
				rol="CAP";
			}
			
			
			String toEmail = usuarioFinzytMB.getUsuarioProyectoRol(usuarioFinzytMB.getProyecto(),rol).getEmail();
			String to[] = {toEmail};
			String subject="LA PECOSA "+pecosa.getPreimpresoinicial()+" SE HA VALIDADO";
			String body="Se ha validado la Pecosa "+pecosa.getPreimpresoinicial()+", ya puede imprimirla.";
			//sendFromEmail(USER_NAME,PASSWORD,to,subject,body);

			return "listadoPECOSA";
		}else{			
			return "consultaPecosa";		
		}
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
	
	public List<Detalleoc>cargarDetalles(){
		
		List<Detalleoc> detalles=new ArrayList<Detalleoc>();		
		
		if(this.ordencompra.getDetalleocs()!=null && this.ordencompra.getDetalleocs().size()>0)
			detalles.addAll(this.ordencompra.getDetalleocs());
		else 
			System.out.print("Esta orden no tiene detalles Da Fuck!?");
		
		return detalles;
	}
	public PecosaService getPecosaService() {
		return pecosaService;
	}
	public void setPecosaService(PecosaService pecosaService) {
		this.pecosaService = pecosaService;
	}
	public List<Pecosa> getPecosas() {
		return pecosas;
	}
	public void setPecosas(List<Pecosa> pecosas) {
		this.pecosas = pecosas;
	}
	public Pecosa getPecosa() {
		return pecosa;
	}
	public void setPecosa(Pecosa pecosa) {
		this.pecosa = pecosa;
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
	public Ordencompra getOrdencompra() {
		return ordencompra;
	}
	public void setOrdencompra(Ordencompra ordencompra) {
		this.ordencompra = ordencompra;
	}	
}
