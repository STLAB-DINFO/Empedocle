package it.unifi.ing.stlab.reflection.model.facts.values;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "DT" )
public class TemporalFactValue extends FactValue {

	private Date date;
	private Boolean useToday;
	
	public TemporalFactValue(String uuid) {
		super(uuid);
	}
	protected TemporalFactValue() {
		super();
	}
	
	
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="use_today")
	public Boolean getUseToday() {
		return useToday;
	}
	public void setUseToday(Boolean useToday) {
		this.useToday = useToday;
	}
	
	
	@Transient
	@Override
	public boolean isEmpty() {
		return ( this.useToday == null || this.useToday == false )
						&& this.getDate() == null;
		
	}
	
	@Override
	public void accept(FactValueVisitor v) {
		v.visitTemporal(this);
		
	}
	
	@Override
	@Transient
	public String getValue() {
		if( useToday )
			return "utilizza data odierna";
		
		if(date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
			sdf.setTimeZone( TimeZone.getDefault() );
			return sdf.format( date );
		}
		else {
			return null;
		}
	}
	
}
