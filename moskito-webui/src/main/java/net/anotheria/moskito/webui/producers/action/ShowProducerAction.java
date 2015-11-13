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
package net.anotheria.moskito.webui.producers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.api.StatLineAO;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.GraphDataValueBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.shared.bean.StatBean;
import net.anotheria.moskito.webui.shared.bean.StatBeanSortType;
import net.anotheria.moskito.webui.shared.bean.StatDecoratorBean;
import net.anotheria.moskito.webui.shared.bean.UnitBean;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Presents a single, previously selected producer.
 * @author another
 *
 */
public class ShowProducerAction extends BaseMoskitoUIAction {
	
	@Override public ActionCommand execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String intervalName = getCurrentInterval(req);
		UnitBean currentUnit = getCurrentUnit(req);

		ProducerAO producer = getProducerAPI().getProducer(req.getParameter(PARAM_PRODUCER_ID), intervalName, currentUnit.getUnit());
		req.setAttribute("producer", producer);

		//copies parameter for producer selection page.
		String target = req.getParameter("target");
		req.setAttribute("target", target);

		//String pFilterZero = req.getParameter(PARAM_FILTER_ZERO);
		//boolean filterZero = pFilterZero != null && pFilterZero.equalsIgnoreCase("true");

		IDecorator decorator = getDecoratorRegistry().getDecorator(producer.getStatsClazzName());
		Map<String, GraphDataBean> graphData = new HashMap<String, GraphDataBean>();


		List<StatLineAO> allLines = producer.getLines();
		for (StatLineAO statLine : allLines){
			try{
				for(StatValueAO statBean : statLine.getValues()){
					String graphKey = decorator.getName()+"_"+statBean.getName();
					GraphDataBean graphDataBean = new GraphDataBean(decorator.getName()+"_"+statBean.getJsVariableName(), statBean.getName());
					graphData.put(graphKey, graphDataBean);
				}
			}catch(ArrayIndexOutOfBoundsException e){
				//producer has no stats at all, ignoring
			}
		}
		
		//bla bla, now prepare view
		List<IDecorator> decorators = new ArrayList<IDecorator>(1);
		decorators.add(decorator);
		List<StatDecoratorBean> beans = new ArrayList<StatDecoratorBean>();
		//sort
		
		StatDecoratorBean b = new StatDecoratorBean();
		b.setName(decorator.getName());
		b.setCaptions(decorator.getCaptions());
		List<StatBean> sbs = new ArrayList<StatBean>();
		for (int i=1; i<allLines.size(); i++){
			StatLineAO line = allLines.get(i);
			List<StatValueAO> statsLine = line.getValues();
			//TODO fix filterzero.
//			if (!filterZero || !s.isEmpty(intervalName)){
				StatBean sb = new StatBean();
				sb.setName(line.getStatName());//s.getName());
				List<StatValueAO> statValues = statsLine;
				for (StatValueAO valueBean : statValues){
					String graphKey = decorator.getName()+"_"+valueBean.getName();
					graphData.get(graphKey).addValue(new GraphDataValueBean(line.getStatName(), valueBean.getRawValue()));
				}
				sb.setValues(statValues);
				sbs.add(sb);
//			}
		}
		b.setStats(StaticQuickSorter.sort(sbs, getStatBeanSortType(b, req)));


		//make cumulated entry
		StatLineAO s = allLines.get(0);
		StatBean sb = new StatBean();
		sb.setName(s.getStatName());//s.getName());
		sb.setValues(s.getValues());
		//
		b.addStatsBean(sb);

		beans.add(b);

		req.setAttribute("decorators", beans);
		req.setAttribute("graphDatas", graphData.values());
		
		inspectProducer(req, producer);
		
		return mapping.findCommand( getForward(req) );
	}
	
	private void inspectProducer(HttpServletRequest req, ProducerAO producer){
		if (! (producer.isInspectable()))
			return;
		CreationInfo cInfo = producer.getCreationInfo();
		req.setAttribute("creationTimestamp", cInfo.getTimestamp());
		req.setAttribute("creationTime", NumberUtils.makeISO8601TimestampString(cInfo.getTimestamp()));
		List<String> stackTraceList = new ArrayList<String>(cInfo.getStackTrace().length);
		for (StackTraceElement elem : cInfo.getStackTrace())
			stackTraceList.add(elem.toString());
		req.setAttribute("creationTrace", stackTraceList);
	}
	
	@Override protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowProducer"+"?"+PARAM_PRODUCER_ID+"="+req.getParameter(PARAM_PRODUCER_ID);
	}

	private StatBeanSortType getStatBeanSortType(StatDecoratorBean decoratorBean, HttpServletRequest req){
		StatBeanSortType sortType;
		String paramSortBy = req.getParameter(decoratorBean.getSortByParameterName());
		if (paramSortBy!=null && paramSortBy.length()>0){
			try{
				int sortBy = Integer.parseInt(paramSortBy);
				String paramSortOrder = req.getParameter(decoratorBean.getSortOrderParameterName());
				boolean sortOrder = paramSortOrder!=null && paramSortOrder.equals("ASC") ? 
						StatBeanSortType.ASC : StatBeanSortType.DESC;
				sortType = new StatBeanSortType(sortBy, sortOrder);
				req.getSession().setAttribute(decoratorBean.getSortTypeName(), sortType);
				return sortType;
			}catch(NumberFormatException skip){}
		}
		sortType = (StatBeanSortType)req.getSession().getAttribute(decoratorBean.getSortTypeName());
		if (sortType==null){
			sortType = new StatBeanSortType();
			req.getSession().setAttribute(decoratorBean.getSortTypeName(), sortType);
		}
		return sortType;
	}
	
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.PRODUCERS;
	}

	@Override
	protected String getPageName() {
		return "producer";
	}

	@Override
	protected boolean exportSupported() {
		return true;
	}

}
