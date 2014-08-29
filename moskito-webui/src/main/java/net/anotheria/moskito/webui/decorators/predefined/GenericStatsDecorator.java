/**
 * (c) 2012 König-Software GmbH - http://www.koenig-software.de
 */
package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.webui.decorators.IDecorator;
import net.anotheria.moskito.webui.producers.api.DoubleValueAO;
import net.anotheria.moskito.webui.producers.api.LongValueAO;
import net.anotheria.moskito.webui.shared.bean.StatCaptionBean;
import net.anotheria.moskito.webui.producers.api.StatValueAO;
import net.anotheria.moskito.webui.producers.api.StringValueAO;
import net.anotheria.util.sorter.IComparable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Micha
 * 
 */
public class GenericStatsDecorator implements IDecorator<GenericStats> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericStatsDecorator.class);

    /**
     * Constructs an instance of GenericStatsDecorator.
     */
    public GenericStatsDecorator() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final IComparable anotherComparable, final int method) {
        return getName().compareToIgnoreCase(((IDecorator) anotherComparable).getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatCaptionBean> getCaptions() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExplanation(final String caption) {
        return "n.a.";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "GenericStats";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatValueAO> getValues(final GenericStats stats,
                                         final String interval,
                                         final TimeUnit unit) {
        final List<String> names = stats.getAvailableValueNames();
        final List<StatValueAO> ret = new ArrayList<StatValueAO>(names.size());

        for (final String name : names) {
            final TypeAwareStatValue sValue = stats.getValueByName(name);
            if (sValue == null) {
                LOGGER.info("unable to determine value for name: " + name);

            } else {
                switch (sValue.getType()) {
                    case DOUBLE:
                        ret.add(new DoubleValueAO(name, sValue.getValueAsDouble(interval)));
                        break;
                    case INT:
                    case LONG:
                        ret.add(new LongValueAO(name, sValue.getValueAsLong(interval)));
                        break;
                    default:
                        ret.add(new StringValueAO(name, sValue.getValueAsString(interval)));
                        break;
                }
            }
        }

        return ret;
    }
}
