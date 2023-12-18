package it.unifi.ing.stlab.reflection.lite.dao;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.reflection.lite.converter.FactConverter;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.lite.converter.jaxb.JaxbConverter;
import it.unifi.ing.stlab.reflection.lite.converter.util.Zipper;
import it.unifi.ing.stlab.reflection.lite.model.facts.context.FactContextLite;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute
public class FactDaoBean implements FactDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Fact findByContext(FactContextLite context) {
		if( context == null ) return null;
		
		byte[] content = context.getContent();
		
		if( content == null ) return null;
		
		byte[] decompressed = Zipper.decompress( content );
		
		FactConverterDao dao = new FactConverterDao( entityManager );
		FactConverter converter = new JaxbConverter( dao );
		Fact result = converter.deserialize( new String( decompressed ) );
		
		AssignContextVisitor visitor = new AssignContextVisitor( context );
		result.accept( visitor );
		
		return result;
	}

	@Override
	public void save(Fact fact) {
		FactConverterDao dao = new FactConverterDao( entityManager );
		FactConverter converter = new JaxbConverter( dao );
		String result = converter.serialize( fact );
		
		byte[] content = Zipper.compress( result.getBytes() );
		FactContextLite context = ClassHelper.cast( fact.getContext(), FactContextLite.class );
		context.setContent( content );
		
		entityManager.merge( context );
	}

}
