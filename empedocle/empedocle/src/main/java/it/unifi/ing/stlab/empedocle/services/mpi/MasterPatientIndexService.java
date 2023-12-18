package it.unifi.ing.stlab.empedocle.services.mpi;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergePatient;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.UpdatePersonInformation;

@WebService( name = "mpiService", targetNamespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/mpiService" )
@SOAPBinding( parameterStyle = SOAPBinding.ParameterStyle.BARE ) // method parameters represent the entire message body; 
																 // the parameters are not elements wrapped inside a top-level 
																 // element named after the operation
public interface MasterPatientIndexService {

	@Oneway
	@WebMethod( operationName = "mergePatient", action = "urn:mpiService.mergePatient" )
	public void merge(
			@WebParam( partName = "body", name = "mergePatient", targetNamespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/mpiService" ) 
			MergePatient body );

	@Oneway
	@WebMethod( operationName = "updatePersonInformation", action = "urn:mpiService.updatePersonInformation" )
	public void update(
			@WebParam( partName = "body", name = "updatePersonInformation", targetNamespace = "http://www.aou-careggi.toscana.it/thislab/eai/people/mpiService" ) 
			UpdatePersonInformation body );
}
