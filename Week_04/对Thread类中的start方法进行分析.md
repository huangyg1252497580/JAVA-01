
>最近在学习并发编程经常用到Thread类中的start()方法，但是不清楚具体这个方法做了些什么，今天特地研究研究。 	

	public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         * 上面这句话的意思大概是：状态为0的时候表示为初始状态'NEW'
         */

        if (threadStatus != 0)//判断当前调用了start()方法的线程是否是初始状态
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. 
         * */
        group.add(this);//经过上面的判断后确定线程为初始状态，会将线程假如到当前的线程组中

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);//线程创建失败会将线程从线程组中移除
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }

	//ThreadGroup类中的group.add(this);方法

     void add(Thread t) {
        synchronized (this) {
            if (destroyed) {//判断当前线程是否被销毁
                throw new IllegalThreadStateException();
            }
            if (threads == null) {
                threads = new Thread[4];//初始化长度为4的Thread数组
            } else if (nthreads == threads.length) {//如果已经使用完空间就会对数组进行扩容 内存翻一倍
                threads = Arrays.copyOf(threads, nthreads * 2);
            }
            threads[nthreads] = t;//将创建的线程假如数组中

            // This is done last so it doesn't matter in case the
            // thread is killed
            nthreads++;//记录成功创建的显数，即使在使用完后kill掉也算

            // The thread is now a fully fledged member of the group, even
            // though it may, or may not, have been started yet. It will prevent
            // the group from being destroyed so the unstarted Threads count is
            // decremented.
            nUnstartedThreads--;//上面对这一句代码的描述大概意思是，这个线程属于当前的线程组，虽然它可能已经run起来也可能还没有run，但是为了防止被销毁就需要把这个线程未启动数-1
        }
    }

	  /**
     * Notifies the group that the thread {@code t} has failed
     * an attempt to start.
     *
     * <p> The state of this thread group is rolled back as if the
     * attempt to start the thread has never occurred. The thread is again
     * considered an unstarted member of the thread group, and a subsequent
     * attempt to start the thread is permitted.
     *
     * @param  t
     *         the Thread whose start method was invoked
     */
    void threadStartFailed(Thread t) {
        synchronized(this) {
            remove(t);
            nUnstartedThreads++;
        }
    }