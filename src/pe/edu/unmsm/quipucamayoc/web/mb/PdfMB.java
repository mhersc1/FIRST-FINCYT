package pe.edu.unmsm.quipucamayoc.web.mb;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;

import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReporteOC;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReporteOS;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.ArticuloReportePecosa;
import pe.edu.unmsm.quipucamayoc.web.utilitarios.Reporte;

@Controller
public class PdfMB {

	public void generarPdf(String reporte, Map<String, Object> paramReport,Collection<ArticuloReporteOC> articulosReporte) {
		FacesContext context = FacesContext.getCurrentInstance();

		ServletContext servletContext = (ServletContext) context
				.getExternalContext().getContext();
		ReportMB report = ReportMB.getInstance(reporte);
		Reporte rep=new Reporte();
		rep.setNombreReporte(reporte);
		rep.setCamposReporte(articulosReporte);
		rep.setParametrosReporte(paramReport);		
		report.addParam(rep);
		boolean rpt = false;
		rpt = report.ejecutarReporte(context, servletContext);
		if (!rpt)
			System.out.print("No se ejecuto el reporte!");
	}
	
	public void generarPdfServicio(String reporte, Map<String, Object> paramReport,Collection<ArticuloReporteOS> articulosReporte) {
		FacesContext context = FacesContext.getCurrentInstance();

		ServletContext servletContext = (ServletContext) context
				.getExternalContext().getContext();
		ReportMB report = ReportMB.getInstance(reporte);
		Reporte rep=new Reporte();
		rep.setNombreReporte(reporte);
		rep.setCamposReporte(articulosReporte);
		rep.setParametrosReporte(paramReport);		
		report.addParam(rep);
		boolean rpt = false;
		rpt = report.ejecutarReporte(context, servletContext);
		if (!rpt)
			System.out.print("No se ejecuto el reporte!");
	}
	
	public void generarPdfPecosa(String reporte, Map<String, Object> paramReport,Collection<ArticuloReportePecosa> articulosReporte) {
		FacesContext context = FacesContext.getCurrentInstance();

		ServletContext servletContext = (ServletContext) context
				.getExternalContext().getContext();
		ReportMB report = ReportMB.getInstance(reporte);
		Reporte rep=new Reporte();
		rep.setNombreReporte(reporte);
		rep.setCamposReporte(articulosReporte);
		rep.setParametrosReporte(paramReport);		
		report.addParam(rep);
		boolean rpt = false;
		rpt = report.ejecutarReporte(context, servletContext);
		if (!rpt)
			System.out.print("No se ejecuto el reporte!");
	}
}
