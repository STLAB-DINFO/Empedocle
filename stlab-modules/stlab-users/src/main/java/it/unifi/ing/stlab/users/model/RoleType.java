package it.unifi.ing.stlab.users.model;


public enum RoleType { 	
	ADMINISTRATOR,
	BASIC_USER,
	DATA_ANALYST,
	DATA_REVIEWER,
	DOMAIN_EXPERT,
	EXAMINATION_EDITOR,
	EXAMINATION_RESCUER,
	PATIENT_EDITOR,
	PATIENT_MERGER;
	
    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}