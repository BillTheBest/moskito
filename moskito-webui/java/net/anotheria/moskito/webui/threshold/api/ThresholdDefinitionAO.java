package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains configuration information about a threshold. Used by js overlay.
 * @author lrosenberg
 *
 */
public class ThresholdDefinitionAO implements Serializable{
	/**
	 * Threshold id.
	 */
	private String id;
	/**
	 * Threshold name.
	 */
	private String name;
	/**
	 * Name of the associated producer.
	 */
	private String producerName;
	/**
	 * Name of the stat.
	 */
	private String statName;
	/**
	 * Name of the value.
	 */
	private String valueName;
	/**
	 * Name of the interval.
	 */
	private String intervalName;
	/**
	 * The description string for this threshold.
	 */
	private String descriptionString;
	/**
	 * The attached guards.
	 */
	private List<ThresholdConditionGuard> guards;

	private TimeUnit timeUnit;
	
	public ThresholdDefinitionAO(){
		guards = new ArrayList<ThresholdConditionGuard>();
	}
	
	public void addGuard(ThresholdConditionGuard aGuard){
		guards.add(aGuard);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	public String getDescriptionString() {
		return descriptionString;
	}

	public void setDescriptionString(String descriptionString) {
		this.descriptionString = descriptionString;
	}

	public List<ThresholdConditionGuard> getGuards() {
		return guards;
	}

	public void setGuards(List<ThresholdConditionGuard> guards) {
		this.guards = guards;
	}

	@Override
	public String toString() {
		return id+"/"+producerName+"/"+statName+"/"+valueName+"/"+valueName;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}
}
