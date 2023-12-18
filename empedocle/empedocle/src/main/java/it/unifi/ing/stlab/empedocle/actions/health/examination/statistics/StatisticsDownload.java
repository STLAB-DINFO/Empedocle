package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics;

import it.unifi.ing.stlab.empedocle.actions.health.examination.ExaminationFilter;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.DataExporter;
import it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.factory.DataExporterFactory;
import it.unifi.ing.stlab.empedocle.dao.health.ExaminationDao;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationStatus;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.Fact;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@Named("stats")
@RequestScoped
@WebServlet(urlPatterns = "/statistics")
public class StatisticsDownload extends HttpServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int DEFAULT_BUFFER_SIZE = 10240;
	private final int DEFAULT_PAGE_SIZE = 100;
	
	@Inject
	private ExaminationFilter examinationFilter;
	
	@Inject
	private ExaminationDao examinationDao;
	
	@Inject
	private TypeDao typeDao;
	
	@Inject
	private FactDao factDao;
	
	@Inject
	private StaffDao staffDao;
	
	@Inject
	private Logger logger;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public StatisticsDownload() {
		super();
	}
	
	// per usarlo come componente CDI in un commandLink
	// (però richieste ajax successive causano un'eccezione, che comunque non pregiudica il funzionamento)
	public void download() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpServletResponse res = (HttpServletResponse)context.getExternalContext().getResponse();
		
		doGet(req, res);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int resultCount = examinationDao.count(examinationFilter);
		
		// se non ho risultati o non sono riuscito a recuperare l'id utente, faccio solo un redirect
		if(resultCount == 0 ) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("");
			dispatcher.forward(request, response);
		}
		
		DataExporter exporter = DataExporterFactory.createDataExporter();
		
		int pageSize = Math.min(DEFAULT_PAGE_SIZE, resultCount);
		
		for(int i = 0; i <= (resultCount / pageSize); i++) {
			List<Examination> resultList = examinationDao.find(examinationFilter, i * pageSize, DEFAULT_PAGE_SIZE);
			List<Fact> rootFacts = new ArrayList<Fact>(resultList.size());
			
			for(Examination ex : resultList) {
				if(ex.getStatus() != ExaminationStatus.TODO ) {
					Fact root = factDao.findByContextId( ex.getId(),
											typeDao.findByExaminationType( ex.getType().getId() ).getId() );
					
					if( root != null)
						rootFacts.add( factDao.fetchForStatistics( ClassHelper.cast( root, FactImpl.class ).getId() ) );
				}
			}
			
			try {
				exporter.export(rootFacts);
			} catch (Exception e) {
				throw new ServletException(e);
			}
			entityManager.clear();
			
		}
		
		String contentType = "application/octet-stream";
//		String contentType = "application/vnd.ms-excel";
		response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", "attachment; filename=" + exporter.getFileName());

        BufferedOutputStream output = null;
        
        try {
            // Open streams
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            exporter.toOutputStream(output);

        } catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
            close(output);
        }
	}
	
	private void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                logger.error("Impossibile chiudere l'output stream creato per il download delle statistiche!", e);
            }
        }
    }
	
}
