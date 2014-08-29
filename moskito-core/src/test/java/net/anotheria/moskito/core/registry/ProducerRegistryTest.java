package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
public class ProducerRegistryTest {
	
	@BeforeClass public static void init(){
		System.setProperty("JUNITTEST", "true");
	}
	
	@Test public void testProducerFunctionallity(){
		IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertNotNull(registry.getProducers());
		
		
		IStatsProducer producer = new Producer();
		registry.registerProducer(producer);
		
		Collection<IStatsProducer> producersFromRegistry = registry.getProducers();
		assertEquals(1, producersFromRegistry.size());
		assertTrue(producersFromRegistry.contains(producer));
	}
	
	@Test public void testListener(){
		IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertNotNull(registry.getProducers());
		
		Listener listener = new Listener();
		registry.addListener(listener);
		
		IStatsProducer producer = new Producer();
		registry.registerProducer(producer);
		
		assertEquals(1, listener.registered);
		assertEquals(0, listener.unregistered);
		
		registry.unregisterProducer(producer);
		assertEquals(1, listener.registered);
		assertEquals(1, listener.unregistered);
		
		registry.removeListener(listener);
		registry.registerProducer(producer);
		registry.unregisterProducer(producer);
		assertEquals(1, listener.registered);
		assertEquals(1, listener.unregistered);

	}
	
	public static final class Listener implements IProducerRegistryListener{

		private int registered = 0;
		private int unregistered = 0;
		
		@Override
		public void notifyProducerRegistered(IStatsProducer producer) {
			registered++;
		}

		@Override
		public void notifyProducerUnregistered(IStatsProducer producer) {
			unregistered++;
		}
		
	}
	
	public static final class Producer implements IStatsProducer{

		@Override
		public String getCategory() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProducerId() {
			return "FOO";
		}

		@Override
		public List<IStats> getStats() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getSubsystem() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Before public void reset(){
		ProducerRegistryFactory.reset();
	}
}
