## 什么是CAS（Compare and Swap） ##
 对于常用多线程编程的人估计知道，对于一般人估计都不曾听说。在jdk5之前,我们知道，在多线程编程的时候，为了保证多个线程对一个对象同时进行访问时,我们需要加同步锁synchronized,保证对象的在使用时的正确性，但是加锁的机制会导致如下几个问题

    1.加多线程竞争下，加锁和释放锁会导致较多的上下文切换，引起性能问题。
    2.多线程可以导致死锁的问题。
    3.多线程持有的锁会导致其他需要此锁的线程挂起。

解决方法：
    
    锁的分类：独占锁（悲观锁），乐观锁
    独占锁：synchronized就是一种独占锁，它会导致所有需要此锁的线程挂起，等待锁的释放。
    乐观锁：每次不加锁去完成操作，如果因为冲突失败就重试，直到成功。

CAS的机制就相当于这种（非阻塞算法），CAS是由CPU硬件实现，所以执行相当快，CAS有三个操作参数：内存地址，期望值，要修改的新值，当期望值和内存当中的值进行比较不相等的时候，表示内存中的值已经被别线程改动过，这时候失败返回，当相等的时候，将内存中的值改为新的值，并返回成功。

代码实现

    public final long getAndIncrement() {
        while (true) {
            long current = get();
            long next = current + 1;
            //当+1操作成功的时候直接返回，退出此循环
            if (compareAndSet(current, next))
                return current;
        }
    }
    //调用JNI实现CAS
    public final boolean compareAndSet(long expect, long update) {
    return unsafe.compareAndSwapLong(this, valueOffset, expect, update);
    }