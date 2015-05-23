package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The basic aspect class.
 *
 * @author lrosenberg
 * @since 19.11.12 00:00
 */
public class AbstractMoskitoAspect<S extends IStats> {

	/**
	 * Map with created producers.
	 */
	private ConcurrentMap<String, OnDemandStatsProducer<S>> producers = new ConcurrentHashMap<String, OnDemandStatsProducer<S>>();

	/**
	 * Returns the producer for the given pjp and producerId. Registers the producer in the registry if it's not already registered.
	 * @param pjp the pjp is used to obtain the producer id automatically if it's not submitted.
	 * @param aProducerId submitted producer id, used if configured in aop.
	 * @param aCategory submitted category.
	 * @param aSubsystem submitted subsystem.
	 * @param withMethod if true the name of the method will be part of the automatically generated producer id.
	 * @return
	 */
	protected  OnDemandStatsProducer<S> getProducer(ProceedingJoinPoint pjp, String aProducerId, String aCategory, String aSubsystem, boolean withMethod, IOnDemandStatsFactory<S> factory, boolean tracingSupported){
		String producerId = null;
		if (aProducerId!=null && aProducerId.length()>0){
			producerId = aProducerId;
		}else{
			producerId = pjp.getSignature().getDeclaringTypeName();
			try{
				producerId = producerId.substring(producerId.lastIndexOf('.')+1);
			}catch(RuntimeException ignored){/* ignored */}
		}

		if (withMethod)
			producerId += "."+pjp.getSignature().getName();

		OnDemandStatsProducer<S> producer = producers.get(producerId);
		if (producer==null){

			producer = new OnDemandStatsProducer(producerId, getCategory(aCategory), getSubsystem(aSubsystem), factory);
			producer.setTracingSupported(tracingSupported);
			OnDemandStatsProducer<S> p = producers.putIfAbsent(producerId, producer);
			if (p==null){
				ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);

				//now check for annotations.
				Class producerClass = pjp.getSignature().getDeclaringType();
				Accumulate annotation = (Accumulate) producerClass.getAnnotation(Accumulate.class);
				if (annotation != null){
					AccumulatorDefinition definition = new AccumulatorDefinition();
					definition.setName(annotation.name());
					definition.setIntervalName(annotation.intervalName());
					definition.setProducerName(producer.getProducerId());
					definition.setStatName("cumulated");
					definition.setValueName(annotation.valueName());
					definition.setTimeUnit(annotation.timeUnit());
					AccumulatorRepository.getInstance().createAccumulator(definition);
				}

			}else{
				producer = p;
			}
		}
		return producer;

	}


	/**
	 * Returns the category to use for the producer registration.
	 * @param proposal
	 * @return
	 */
	public String getCategory(String proposal) {
		return proposal==null || proposal.length()==0 ? "annotated" : proposal;
	}

	/**
	 * Returns the subsystem for registration.
	 * @param proposal
	 * @return
	 */
	public String getSubsystem(String proposal) {
		return proposal==null || proposal.length()==0 ? "default" : proposal;
	}

	public void reset() {
		producers.clear();
	}

}
