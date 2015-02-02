package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 29.10.12 00:21
 */
public class AdditionalSectionAction extends BaseAdditionalAction{
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskMore?ts="+System.currentTimeMillis();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "more";
	}

}
