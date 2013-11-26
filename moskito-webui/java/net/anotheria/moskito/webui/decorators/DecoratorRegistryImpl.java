/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.anotheria.moskito.webui.decorators;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.GuestBasicPremiumStats;
import net.anotheria.moskito.core.counter.MaleFemaleStats;
import net.anotheria.moskito.core.predefined.ActionStats;
import net.anotheria.moskito.core.predefined.CacheStats;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.predefined.MemoryPoolStats;
import net.anotheria.moskito.core.predefined.MemoryStats;
import net.anotheria.moskito.core.predefined.OSStats;
import net.anotheria.moskito.core.predefined.RuntimeStats;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServletStats;
import net.anotheria.moskito.core.predefined.ThreadCountStats;
import net.anotheria.moskito.core.predefined.ThreadStateStats;
import net.anotheria.moskito.core.predefined.VirtualMemoryPoolStats;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.util.storage.StorageStats;
import net.anotheria.moskito.web.session.SessionCountStats;
import net.anotheria.moskito.webui.decorators.counter.CounterStatsDecorator;
import net.anotheria.moskito.webui.decorators.counter.GuestBasicPremiumStatsDecorator;
import net.anotheria.moskito.webui.decorators.counter.MaleFemaleStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ActionStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.CacheStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.FilterStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.GenericStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.MemoryPoolStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.MemoryStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.OSStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.RuntimeStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ScopeCountDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ServiceStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ServletStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.SessionCountDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ThreadCountDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ThreadStatesDecorator;
import net.anotheria.moskito.webui.decorators.util.StorageStatsDecorator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the IDecoratorRegistry.
 * @author lrosenberg
 */
public class DecoratorRegistryImpl implements IDecoratorRegistry{

	/**
	 * Internal decorator map.
	 */
	private Map<Class<? extends AbstractStats>,IDecorator> registry;
	/**
	 * Default decorator instance for missing decorators.
	 */
	private IDecorator defaultDecorator;
	
	@Override public IDecorator getDecorator(IStats stats) {
		IDecorator specificDecorator = registry.get(stats.getClass());
		return specificDecorator == null ? defaultDecorator : specificDecorator;
	}
	
	@Override public List<IDecorator> getDecorators(){
		List<IDecorator> ret = new ArrayList<IDecorator>();
		ret.addAll(registry.values());
		return ret;
	}

	DecoratorRegistryImpl(){
		registry = new HashMap<Class<? extends AbstractStats>,IDecorator>();
		configure();
	}
	
	//leon: replace this hard-wired-method with a property or xml config one day
	private void configure(){
		defaultDecorator = new DefaultDecorator();
		registry.put(ServiceStats.class, new ServiceStatsDecorator());
		registry.put(ActionStats.class, new ActionStatsDecorator());
		registry.put(ServletStats.class, new ServletStatsDecorator());
		registry.put(FilterStats.class, new FilterStatsDecorator());
		registry.put(CacheStats.class, new CacheStatsDecorator());
		registry.put(StorageStats.class, new StorageStatsDecorator());
		registry.put(MemoryStats.class, new MemoryStatsDecorator());
		registry.put(MemoryPoolStats.class, new MemoryPoolStatsDecorator());
		registry.put(VirtualMemoryPoolStats.class, new MemoryPoolStatsDecorator("VirtualMemoryPool"));
		registry.put(SessionCountStats.class, new SessionCountDecorator());
		registry.put(ThreadCountStats.class, new ThreadCountDecorator());
		registry.put(ThreadStateStats.class, new ThreadStatesDecorator());
		registry.put(OSStats.class, new OSStatsDecorator());
		registry.put(RuntimeStats.class, new RuntimeStatsDecorator());
		registry.put(GenericStats.class, new GenericStatsDecorator());

		//counters
		registry.put(CounterStats.class, new CounterStatsDecorator());
		registry.put(MaleFemaleStats.class, new MaleFemaleStatsDecorator());
		registry.put(GuestBasicPremiumStats.class, new GuestBasicPremiumStatsDecorator());

		//this can be present at runtime but can be also not present. Therefore we catch this exception.
		//if moskito-cdi is in the classpath we register the decorator.
		try{
			Class scsClass = Class.forName("net.anotheria.moskito.integration.cdi.scopes.ScopeCountStats");
			registry.put(scsClass, new ScopeCountDecorator());
		}catch(ClassNotFoundException e){
			//ignoring this exception,
		}
	}
	
	@Override public void addDecorator(Class <? extends AbstractStats> clazz, IDecorator decorator){
		registry.put(clazz, decorator);
	}
	
}
