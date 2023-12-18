package it.unifi.ing.stlab.users.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.traced.Actor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name = "users" )
public class User implements Actor, Persistable {

	private PersistableImpl persistable;
	private String userid;
	private String password;
	private String name;
	private String surname;
	private String phone;
	private String mail;
	private Set<Role> roles;
	private Set<Qualification> qualifications;
	private Boolean isDeprecated;

	public User( String uuid ) {
		persistable = new PersistableImpl( uuid );
		roles = new HashSet<Role>();
		qualifications = new HashSet<Qualification>();
	}
	protected User() {
		persistable = new PersistableImpl();
		roles = new HashSet<Role>();
		qualifications = new HashSet<Qualification>();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="user", 
		allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}

	// UUID
	public String getUuid() {
		return persistable.getUuid();
	}
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	
	// Userid
	@Column( unique = true )
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	// Password
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	// Surname
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
	// Mail
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	// Phone
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@ManyToMany
	@JoinTable(
		name = "user_roles",
	    joinColumns = { @JoinColumn( name = "user_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "role_id", referencedColumnName = "id") } )
	protected Set<Role> getRoles() {
		return roles;
	}
	protected void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Set<Role> listRoles() {
		return Collections.unmodifiableSet( roles );
	}
	public void addRole( Role role ) {
		if ( role == null ) return;
		
		roles.add( role );
	}
	public void removeRole( Role role ) {
		roles.remove( role );
	}
	public void clearRoles() {
		roles.clear();
	}
	

	@ManyToMany
	@JoinTable(
		name = "user_qualifications",
	    joinColumns = { @JoinColumn( name = "user_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "qual_id", referencedColumnName = "id") } )
	protected Set<Qualification> getQualifications() {
		return qualifications;
	}
	protected void setQualifications(Set<Qualification> qualifications) {
		this.qualifications = qualifications;
	}
	public Set<Qualification> listQualifications() {
		return Collections.unmodifiableSet( qualifications );
	}
	public void addQualification( Qualification qualification ) {
		if ( qualification == null ) return;
		
		qualifications.add( qualification );
	}
	public void removeQualification( Qualification qualification ) {
		qualifications.remove( qualification );
	}
	public void clearQualifications() {
		qualifications.clear();
	}
	
	
	//isDeprecated
	@Column( name = "deprecated" )
	public Boolean getIsDeprecated() {
		return isDeprecated;
	}
	public void setIsDeprecated(Boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}
	
	@PrePersist
	public void prePersist() {
	    if(isDeprecated == null)
	    	isDeprecated = false;
	}
	
	//
	// Methods
	//
	
	@Transient
	public boolean isValidPassword( String password ) {
		PasswordHash passwordHash = new PasswordHash();
		
		return passwordHash.createPasswordKey(password).equals( this.password );
	}
	
	public Boolean hasRole(String roleName) {
		for ( Role r : listRoles() ) {
			if ( roleName.equals( r.getName() )) {
				return true;
			}
		}
		
		return false;
	}
	
	public int hashCode() {
		return persistable.hashCode();
	}

	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
}
