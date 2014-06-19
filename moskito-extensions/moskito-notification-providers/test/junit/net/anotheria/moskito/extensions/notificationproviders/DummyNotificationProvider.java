package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.10.12 00:12
 */
public class DummyNotificationProvider implements NotificationProvider{
	private CountDownLatch latch;
	private static volatile DummyNotificationProvider instance;

	public DummyNotificationProvider(){
		instance = this;
	}

	@Override
	public void configure(NotificationProviderConfig config) {
		latch = new CountDownLatch(Integer.parseInt(config.getProperties().get("count")));
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		latch.countDown();
	}

	public void await(int millis) throws InterruptedException{
		latch.await(millis, TimeUnit.MILLISECONDS);
	}

	public static DummyNotificationProvider getInstance(){
		return instance;
	}
}
