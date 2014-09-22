package pe.edu.unmsm.quipucamayoc.web.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReporteOC;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.Reporte;

public class ReportMB {
private static ReportMB reportMB;
private static Reporte reporte; 
private static final String URL = "/reportes/";
public static ReportMB getInstance(String reporte){
	if(reportMB==null){
		reportMB=new ReportMB();
	}
	reportMB.setReportParam(ReportMB.getInstanceReporte(reporte));
	return reportMB;
}
private void setReportParam(Reporte instanceReporte) {
	ReportMB.reporte = instanceReporte;

}
public static Reporte getInstanceReporte(String nombreReporte){
	if(reporte==null){
		reporte=new Reporte();
	}
	reporte.setCamposReporte(new ArrayList<ArticuloReporteOC>());
	reporte.setParametrosReporte(new HashMap<String, Object>());	
	reporte.setNombreReporte(nombreReporte);
	return reporte;
}
public void addParam(Reporte report) {
	reporte.setNombreReporte(report.getNombreReporte());
	reporte.setCamposReporte(report.getCamposReporte());
	reporte.setParametrosReporte(report.getParametrosReporte());
}
public boolean ejecutarReporte(FacesContext context, ServletContext servletContext){
	HttpServletRequest request = (HttpServletRequest) context
			.getExternalContext().getRequest();
	HttpServletResponse response = (HttpServletResponse) context
			.getExternalContext().getResponse();
	JasperPrint jasperPrint = execReport(context, servletContext);
	
	if(jasperPrint!=null){
		List<JRPrintPage> lista=jasperPrint.getPages();
		jasperPrint.setLocaleCode("en_US");
		if (lista != null && lista.size() > 0) {
				exportarReporteaPDF(jasperPrint, context, response, request);
			return true;
		}	
	}
	return false;
}

private JasperPrint execReport(FacesContext context,
		ServletContext servletContext) {
	try {
		JasperReport report = null;
		JasperPrint jasperPrint;
		String archivo;
		archivo = servletContext.getRealPath(URL
				+ reporte.getNombreReporte() + ".jrxml");

		if (!archivo.equals("")) {
			report = JasperCompileManager.compileReport(archivo);

		} else {
			
		}
		if(reporte.getCamposReporte()!=null){			
			JRDataSource dataSource=new JRBeanCollectionDataSource(reporte.getCamposReporte());
			jasperPrint = JasperFillManager.fillReport(report,
					reporte.getParametrosReporte() ,dataSource);			
		}else{
			jasperPrint = JasperFillManager.fillReport(report,
					reporte.getParametrosReporte());
			
		}

		return jasperPrint;

	} catch (JRException e) {
		// log.error(e);
		e.printStackTrace();
	} catch (Exception e) {
		// log.error(e);
		e.printStackTrace();
	}
	return new JasperPrint();
}

private void exportarReporteaPDF(JasperPrint jasperPrint,
		FacesContext context, HttpServletResponse response,
		HttpServletRequest request) {
	byte[] pdf;
	try{
	pdf=JasperExportManager.exportReportToPdf(jasperPrint);
	response.addHeader("Content-disposition", "attachment;filename="
			+ reporte.getNombreReporte() + ".pdf");
	response.setContentLength(pdf.length);
	response.getOutputStream().write(pdf);
	response.setContentType("application/pdf");	
	context.responseComplete();
	}catch(Exception e){
		System.out.print("Mensaje de la Excepcion:"+e.getMessage());
	}
}

}