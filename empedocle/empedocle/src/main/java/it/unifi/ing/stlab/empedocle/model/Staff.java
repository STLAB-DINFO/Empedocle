package it.unifi.ing.stlab.empedocle.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.users.model.User;

@Entity
@Table(name = "staff")
public class Staff implements Persistable {
 
	private PersistableImpl persistable;
	private User user;
	private String number; // matricola/codice fiscale
	private Phenomenon phenomenon;
	private Agenda defaultAgenda;
	private Set<Agenda> agendas;
	private Set<Agenda> favoriteAgendas;

	public Staff( String uuid ) {
		persistable = new PersistableImpl( uuid );
		agendas = new HashSet<Agenda>();
		favoriteAgendas = new HashSet<Agenda>();
	}
	protected Staff() {
		persistable = new PersistableImpl();
		agendas = new HashSet<Agenda>();
		favoriteAgendas = new HashSet<Agenda>();		
	}

	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="staff", 
		allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}

	
	public String getUuid() {
		return persistable.getUuid();
	}
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	
	@OneToOne( cascade = { CascadeType.ALL } )
	@JoinColumn( name = "user_id" )
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne( cascade = { CascadeType.ALL } )
	@JoinColumn( name = "phen_id" )
	public Phenomenon getPhenomenon() {
		return phenomenon;
	}
	public void setPhenomenon(Phenomenon phenomenon) {
		this.phenomenon = phenomenon;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@ManyToOne
	@JoinColumn( name = "default_agenda_id")
	public Agenda getDefaultAgenda() {
		return defaultAgenda;
	}
	public void setDefaultAgenda(Agenda defaultAgenda) {
		this.defaultAgenda = defaultAgenda;
	}
	
	
	@ManyToMany
	@JoinTable(
		name = "staff_agendas",
	    joinColumns = { @JoinColumn( name = "staff_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "agenda_id", referencedColumnName = "id") } )
	protected Set<Agenda> getAgendas() {
		return agendas;
	}
	protected void setAgendas(Set<Agenda> agendas) {
		this.agendas = agendas;
	}
	public Set<Agenda> listAgendas() {
		return Collections.unmodifiableSet( agendas );
	}
	public void addAgenda( Agenda agenda ) {
		if ( agenda == null ) return;
		
		agendas.add( agenda );
	}
	public void removeAgenda( Agenda agenda ) {
		agendas.remove( agenda );
	}
	public void clearAgendas(){
		agendas.clear();
	}
	
	@Transient
	public List<Agenda> listAgendasOrdered() {	
		ArrayList<Agenda> result = new ArrayList<Agenda>( agendas );
		Collections.sort( result, new Comparator<Agenda>() {

			@Override
			public int compare( Agenda o1, Agenda o2 ) {
				return o1.getCode().compareTo( o2.getCode() );
			}
		});
	
		return result;
	}
	
	@ManyToMany
	@JoinTable(
		name = "favorite_agendas",
	    joinColumns = { @JoinColumn( name = "staff_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "agenda_id", referencedColumnName = "id") } )
	protected Set<Agenda> getFavoriteAgendas() {
		return favoriteAgendas;
	}
	protected void setFavoriteAgendas(Set<Agenda> favoriteAgendas) {
		this.favoriteAgendas = favoriteAgendas;
	}
	public Set<Agenda> listFavoriteAgendas() {
		return Collections.unmodifiableSet( favoriteAgendas );
	}	
	public void addFavoriteAgenda( Agenda agenda ) {
		if ( agenda == null ) return;
		
		favoriteAgendas.add( agenda );
	}
	public void removeFavoriteAgenda( Agenda agenda ) {
		favoriteAgendas.remove( agenda );
	}
	public void clearFavoriteAgendas(){
		favoriteAgendas.clear();
	}	
	
	@Transient
	public List<Agenda> listFavoriteAgendasOrdered() {	
		ArrayList<Agenda> result = new ArrayList<Agenda>( favoriteAgendas );
		Collections.sort( result, new Comparator<Agenda>() {

			@Override
			public int compare( Agenda o1, Agenda o2 ) {
				return o1.getCode().compareTo( o2.getCode() );
			}
		});
	
		return result;
	}
	

	//
	// HashCode & Equals
	//
	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
}
