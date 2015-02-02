package net.anotheria.moskito.core.usecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manager for use-cases.
 * @author lrosenberg
 *
 */
public class UseCaseManager {
	
	/**
	 * Stored use cases.
	 */
	private Map<String, UseCase> useCases;
		
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(UseCaseManager.class);
	
	
	/**
	 * Package protected constructor.
	 */
	UseCaseManager(){
		useCases = new ConcurrentHashMap<String,UseCase>();
	}
	/**
	 * Adds a use case.
	 * @param useCaseName
	 */
	public void addUseCase(String useCaseName){
		UseCase u = new UseCase(useCaseName);
		if (!useCases.containsKey(useCaseName))
			useCases.put(useCaseName, u);
		else
			log.warn("Trying to overwrite useCase: "+useCaseName);
			
	}
	/**
	 * Returns the use case by the use case name.
	 * @param useCaseName
	 * @return
	 */
	public UseCase getUseCase(String useCaseName){
		return useCases.get(useCaseName);
	}
}
