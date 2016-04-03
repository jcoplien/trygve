package info.fulloo.trygve.run_time;

import java.util.concurrent.locks.ReentrantLock;

public class RTMutex {
	public RTMutex() {
		final boolean fair = true;	// fair == slow
		semaphore_ = new ReentrantLock(fair);
		unlockKey_ = null;
	}
	public synchronized void acquireWithKey(final Object key) {
		if (unlockKey_ == key) {
//System.err.format("acquireWithKey on thread %s: already acquired\n", currentThread_);
			return;
		}
		assert key != null;
		acquire();
		unlockKey_ = key;
	}
	public synchronized void acquire() {
		// boolean interrupted;
		do {
			// interrupted = false;
			try {
				if (Thread.holdsLock(semaphore_)) break;
				semaphore_.lock();
				unlockKey_ = null;	// may be overwritten by acquireWithKey
				currentThread_ = Thread.currentThread().getName();
//System.err.format("\tacquire on thread %s\n", currentThread_);
			} catch (Throwable e) {
				//interrupted = true;
			}
		} while (false);

		currentThread_ = Thread.currentThread().getName();
	}
	public synchronized void release() {
//System.err.format("currentThread \"%s\" released\n",  currentThread_);
		if (unlockKey_ == null) {
			currentThread_ = null;
			semaphore_.unlock();
		} else {
			assert false;
		}
	}
	public synchronized void releaseWithKey(final Object key) {
//System.err.format("releaseWithKey on thread %s\n", currentThread_);
		if (key == unlockKey_) {
			unlockKey_ = null;
			currentThread_ = null;
			semaphore_.unlock();
		} else {
			//assert false;
		}
	}
	
	private volatile ReentrantLock semaphore_;
	private volatile Object unlockKey_;
	private volatile String currentThread_;
}
