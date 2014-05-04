package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;

import javax.servlet.http.HttpServletRequest;

/**
 * Base action for accumulators.
 * @author lrosenberg
 */
public abstract class BaseAccumulatorsAction extends BaseMoskitoUIAction {

	/**
	 * Accumulator api instance.
	 */
	private static AccumulatorAPI accumulatorAPI = APIFinder.findAPI(AccumulatorAPI.class);

    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.ACCUMULATORS;
    }
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "";
    }

	protected AccumulatorAPI getAccumulatorAPI(){
		return APILookupUtility.getAccumulatorAPI();
	}

	@Override
	protected String getSubTitle() {
		return "Accumulators";
	}
}
