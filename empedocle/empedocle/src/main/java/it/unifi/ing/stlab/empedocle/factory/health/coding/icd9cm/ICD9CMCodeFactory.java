package it.unifi.ing.stlab.empedocle.factory.health.coding.icd9cm;

import it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm.ICD9CMCategoryCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm.ICD9CMSubCategoryCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm.ICD9CMSubClassificationCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm.ICD9CMSubTopicCode;
import it.unifi.ing.stlab.empedocle.model.health.coding.icd9cm.ICD9CMTopicCode;

import java.util.UUID;

public class ICD9CMCodeFactory {

	public static ICD9CMTopicCode createTopicCode() {
		return new ICD9CMTopicCode( UUID.randomUUID().toString() );
	}
	
	public static ICD9CMSubTopicCode createSubTopicCode() {
		return new ICD9CMSubTopicCode( UUID.randomUUID().toString() );
	}
	
	public static ICD9CMCategoryCode createCategoryCode() {
		return new ICD9CMCategoryCode( UUID.randomUUID().toString() );
	}
	
	public static ICD9CMSubCategoryCode createSubCategoryCode() {
		return new ICD9CMSubCategoryCode( UUID.randomUUID().toString() );
	}
	
	public static ICD9CMSubClassificationCode createSubClassificationCode() {
		return new ICD9CMSubClassificationCode( UUID.randomUUID().toString() );
	}
	
}
