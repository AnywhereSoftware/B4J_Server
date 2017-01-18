package anywheresoftware.b4j.object;

import java.lang.reflect.Method;

import anywheresoftware.b4a.BA;

public class BackgroundWorkersManager {
	private final boolean debug;
	private final BA ba;
	public BackgroundWorkersManager(BA ba, boolean debug) {
		this.debug = debug;
		this.ba = ba;
	}
	public void startWorker(final Class<?> className) {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					Method initializeMethod = JServlet.getInitializeMethod(className);
					JServlet.createInstance(className, initializeMethod);
					System.out.println("Worker ended (" + className + ")");
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if (debug) {
			ba.postRunnable(r);
		}
		else {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.start();
		}
	}
}
