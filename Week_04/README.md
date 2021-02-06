## 多线程代码实例分析 ##
    public class Join {
    
    public static void main(String[] args) {
        Object oo = new Object();
    
        MyThread thread1 = new MyThread("thread1 -- ");
        //oo = thread1;
        thread1.setOo(oo);
        thread1.start();
        
        synchronized (oo) {  // 这里用oo或thread1/this
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        oo.wait(0);
                        //thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " --join-- " + i);
            }
        }
	  }
	}
	class MyThread extends Thread {
    
    private String name;
    private Object oo;
    
    public void setOo(Object oo) {
        this.oo = oo;
    }

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        synchronized (oo) { // 这里用oo或this，效果不同
            for (int i = 0; i < 100; i++) {
                System.out.println(name +"--MyThread--"+ i);
            }
        }
    }

在上面的栗子中两个同步代码块使用的都是统一把锁oo，在主线程中i==20之后oo对象就调用wait()方法，主线程就会释放锁，这个时候thread1.start()创建出来的线程会获取到锁就会执行MyThread类中的run()方法，但是这个MyThread中的run()方法执行完之后整个进程也会随之结束并没有继续执行main方法中的for循环打印


这个时候修上面的代码，main方法中oo = thread1;MyThread类中同步代码块锁的对象为this当前调用者

    public class Join {
    
    public static void main(String[] args) {
        Object oo = new Object();
    
        MyThread thread1 = new MyThread("thread1 -- ");
        oo = thread1;
        thread1.setOo(oo);
        thread1.start();
        synchronized (oo) {  // 这里用oo或thread1/this
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        oo.wait(0);
                        //thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                System.out.println(Thread.currentThread().getName() + " --join-- " + i);
            }
        }
      }
	}

	class MyThread extends Thread {

	    private String name;
	    private Object oo;
	    
	    public void setOo(Object oo) {
	        this.oo = oo;
	    }
	
	    public MyThread(String name) {
	        this.name = name;
	    }
	
	    @Override
	    public void run() {
	        synchronized (this) { // 这里用oo或this，效果不同
	            for (int i = 0; i < 100; i++) {
	                System.out.println(name +"--MyThread--"+ i);
	            }
	        }
    	}
	}


这个时候mian方法中锁的对象是thread1和MyThread类中同步代码块锁的是同一个对象，当oo.wait(0);执行释放锁了之后MyThread开启的线程拿到对象锁执行同步代码块，与前面的情况不同的是，这里执行完之后不需要显示调用notify()主线程也会被唤醒继续执行输出打印的语句

----------

问题1：
	为什么当main方法中的同步代码块和MyThread中的同步代码快使用的是同一个MyThread对象的锁时候在main方法里wait()之后不需要使用notify唤醒也可以在MyThread同步代码块执行完之后继续main方法中wait()之后的代码？是不是因为使用的是同一个MyThread对象不需要使用notify()唤醒也可以让main方法中的for循环继续执行


----------

## 到底什么是锁 ##

    synchronized方式的问题：
    1、同步块的阻塞无法中断（不能Interruptibly）
    2、同步块的阻塞无法控制超时（无法自动解锁）
    3、同步块无法异步处理锁（即不能立即知道是否可以拿到锁）
    4、同步块无法根据条件灵活的加锁解锁（即只能跟同步块范围一致）

如何解决synchronized的问题？
我们可以使用粒度更小的LOCK锁，lock有一下优点

1. 使用方式灵活可控
2. 性能开销小
3. 锁工具包:  java.util.concurrent.locks

>

	Lock接口设计：
	// 1.支持中断的API
	void lockInterruptibly() throws InterruptedException;
	// 2.支持超时的API
	boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
	// 3.支持非阻塞获取锁的API
	boolean tryLock();