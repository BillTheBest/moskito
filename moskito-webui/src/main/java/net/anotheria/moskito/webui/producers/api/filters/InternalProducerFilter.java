package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.tracers.api.TracerAPI;

import java.util.HashSet;

/**
 * This filter removes internally used producers like threshold api etc.
 *
 * @author lrosenberg
 * @since 20.04.15 00:01
 */
public class InternalProducerFilter implements ProducerFilter {

	private final HashSet<String> internalProducerNames;

	public InternalProducerFilter(){
		internalProducerNames = new HashSet<>(9);
		internalProducerNames.add(ProducerAPI.class.getSimpleName());
		internalProducerNames.add(ThresholdAPI.class.getSimpleName());
		internalProducerNames.add(AccumulatorAPI.class.getSimpleName());
		internalProducerNames.add(AdditionalFunctionalityAPI.class.getSimpleName());
		internalProducerNames.add(GaugeAPI.class.getSimpleName());
		internalProducerNames.add(DashboardAPI.class.getSimpleName());
		internalProducerNames.add(ThreadAPI.class.getSimpleName());
		internalProducerNames.add(JourneyAPI.class.getSimpleName());
		internalProducerNames.add(TracerAPI.class.getSimpleName());
	}

	@Override
	public void customize(String parameter) {
		//do nothing
	}

	@Override
	public boolean mayPass(IStatsProducer<?> statsProducer) {
		return !internalProducerNames.contains(statsProducer.getProducerId());
	}
}
