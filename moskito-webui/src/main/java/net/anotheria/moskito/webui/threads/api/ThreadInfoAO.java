package net.anotheria.moskito.webui.threads.api;

import java.io.Serializable;
import java.lang.management.ThreadInfo;

/**
 * Mapped JMX ThreadInfo class.
 *
 * @author lrosenberg
 * @since 21.03.14 23:32
 */
public class ThreadInfoAO implements Serializable{
	/**
	 * Internal id of the thread.
	 */
	private long threadId;
	/**
	 * Name of the thread.
	 */
	private String threadName;
	/**
	 * State the thread is in.
	 */
	private Thread.State threadState;
	/**
	 * If true the thread is in a native stack.
	 */
	private boolean inNative;
	/**
	 * If true the thread is suspended.
	 */
	private boolean suspended;

	/**
	 * Name of the lock if applicable.
	 */
	private String lockName;
	/**
	 * If of the lock owner id.
	 */
	private long lockOwnerId;
	/**
	 * Name of the lock owner.
	 */
	private String lockOwnerName;

	/**
	 * Number of times the thread was in BLOCKED mode.
	 */
	private long blockedCount;
	/**
	 * Time spent blocked.
	 */
	private long blockedTime;

	/**
	 * Number of times the thread was in WAITING mode.
	 */
	private long waitedCount;
	/**
	 * Time spent waiting.
	 */
	private long waitedTime;


	private StackTraceElement[] stackTrace;

	public ThreadInfoAO(ThreadInfo jmxInfo){
		threadId = jmxInfo.getThreadId();
		threadName = jmxInfo.getThreadName();
		threadState = jmxInfo.getThreadState();

		inNative = jmxInfo.isInNative();
		suspended = jmxInfo.isSuspended();

		lockName = jmxInfo.getLockName();
		lockOwnerName = jmxInfo.getLockOwnerName();
		lockOwnerId = jmxInfo.getLockOwnerId();

		blockedCount = jmxInfo.getBlockedCount();
		blockedTime = jmxInfo.getBlockedTime();

		waitedCount = jmxInfo.getWaitedCount();
		waitedTime = jmxInfo.getWaitedTime();
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Thread.State getThreadState() {
		return threadState;
	}

	public void setThreadState(Thread.State threadState) {
		this.threadState = threadState;
	}

	public boolean isInNative() {
		return inNative;
	}

	public void setInNative(boolean inNative) {
		this.inNative = inNative;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}

	public long getLockOwnerId() {
		return lockOwnerId;
	}

	public void setLockOwnerId(long lockOwnerId) {
		this.lockOwnerId = lockOwnerId;
	}

	public String getLockOwnerName() {
		return lockOwnerName;
	}

	public void setLockOwnerName(String lockOwnerName) {
		this.lockOwnerName = lockOwnerName;
	}

	public long getBlockedCount() {
		return blockedCount;
	}

	public void setBlockedCount(long blockedCount) {
		this.blockedCount = blockedCount;
	}

	public long getBlockedTime() {
		return blockedTime;
	}

	public void setBlockedTime(long blockedTime) {
		this.blockedTime = blockedTime;
	}

	public long getWaitedCount() {
		return waitedCount;
	}

	public void setWaitedCount(long waitedCount) {
		this.waitedCount = waitedCount;
	}

	public long getWaitedTime() {
		return waitedTime;
	}

	public void setWaitedTime(long waitedTime) {
		this.waitedTime = waitedTime;
	}
	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = stackTrace;
	}

}
