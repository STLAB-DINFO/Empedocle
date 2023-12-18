package it.unifi.ing.stlab.empedocle.actions.health.examination.consent;

import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.patients.dao.PatientDao;
import it.unifi.ing.stlab.patients.model.Patient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Named("patient-consent")
@RequestScoped
@WebServlet(urlPatterns = "/patient-consent")
public class PatientConsentEnricher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private PatientDao patientDao;

	public PatientConsentEnricher() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Patient patient = patientDao.findById( new Long(request.getParameter("pid")));
		
		PdfReader reader = new PdfReader(
				"http://" + request.getServerName() + ":" + request.getServerPort() 
				+ "/empedocle-content/common/consenso_trattam_dati_non_degenti_M903D02_C.pdf");
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=" + "consenso_trattamento_dati_non_degenti.pdf");
		OutputStream output = response.getOutputStream();
		
		try {
			PdfStamper stamper = new PdfStamper(reader, output);

			@SuppressWarnings("unchecked")
			HashMap<String, String> info = reader.getInfo();
			info.put(
					"Title",
					"Trattamento dei dati personali - Modulo di consenso e ulteriori determinazioni non degenti");
			stamper.setMoreInfo(info);
			
			PdfContentByte over = stamper.getOverContent(1);
			over.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA,
			          BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 10);
			
			over.beginText();
			
			// print name and surname
			over.setTextMatrix(120, 665);
			over.showText(patient.getName() + " " + patient.getSurname());
			
			// print birth place
			over.setTextMatrix(120, 648);
			over.showText(patient.getBirthPlace());
			
			// print birth date
			over.setTextMatrix(440, 648);
			over.showText( DateUtils.getString(patient.getBirthDate(), "dd/MM/yyyy"));
			
			over.endText();
			
			stamper.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
