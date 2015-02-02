package net.anotheria.moskito.core.threshold.alerts.notificationprovider;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.10.12 10:22
 */
public class SyserrNotificationProvider implements NotificationProvider {
	@Override
	public void configure(NotificationProviderConfig config) {

	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		try{
			System.err.println("NEW ThresholdAlert: "+alert);
		}catch(Exception any){
			any.printStackTrace();
		}
	}
}
