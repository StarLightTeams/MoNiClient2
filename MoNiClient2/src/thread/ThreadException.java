package thread;

/**
 * �����߳��쳣��
 */
public class ThreadException implements Thread.UncaughtExceptionHandler{

	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("�߳�["+t.getName()+":"+t.getId()+"]:"+e.getClass().getSimpleName()+"---"+e.getMessage());
	}
	
}
