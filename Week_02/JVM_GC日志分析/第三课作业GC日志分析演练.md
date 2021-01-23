# 第三课作业-GC分析演练 #
## 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例 ##
 **在本地运行GCLogAnalysis.java使用java8默认的并行gc进行测试**

    [GC (Allocation Failure) [PSYoungGen: 64864K->10749K(75776K)] 64864K->19414K(249344K), 0.0062655 secs] [Times: user=0.00 sys=0.06, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 75773K->10725K(140800K)] 84438K->40811K(314368K), 0.0113732 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 140773K->10743K(140800K)] 170859K->85174K(314368K), 0.0129395 secs] [Times: user=0.03 sys=0.05, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 140791K->10745K(270848K)] 215222K->122948K(444416K), 0.0097621 secs] [Times: user=0.01 sys=0.06, real=0.01 secs] 
	[GC (Allocation Failure) [PSYoungGen: 270841K->10749K(270848K)] 383044K->209442K(470528K), 0.0211678 secs] [Times: user=0.03 sys=0.03, real=0.02 secs] 
	[Full GC (Ergonomics) [PSYoungGen: 10749K->0K(270848K)] [ParOldGen: 198692K->171820K(340992K)] 209442K->171820K(611840K), [Metaspace: 3428K->3428K(1056768K)], 0.0267731 secs] [Times: user=0.06 sys=0.02, real=0.03 secs] 
	[GC (Allocation Failure) [PSYoungGen: 260096K->81736K(545280K)] 431916K->253557K(886272K), 0.0257578 secs] [Times: user=0.01 sys=0.06, real=0.03 secs] 
	[GC (Allocation Failure) [PSYoungGen: 545096K->110078K(592384K)] 716917K->364594K(933376K), 0.0422600 secs] [Times: user=0.11 sys=0.09, real=0.04 secs] 
	[Full GC (Ergonomics) [PSYoungGen: 110078K->0K(592384K)] [ParOldGen: 254515K->286140K(492032K)] 364594K->286140K(1084416K), [Metaspace: 3428K->3428K(1056768K)], 0.0415955 secs] [Times: user=0.14 sys=0.00, real=0.04 secs] 
	[GC (Allocation Failure) [PSYoungGen: 482304K->140327K(978944K)] 768444K->426468K(1470976K), 0.0310124 secs] [Times: user=0.05 sys=0.11, real=0.03 secs] 
	[GC (Allocation Failure) [PSYoungGen: 948263K->188405K(996352K)] 1234404K->554642K(1488384K), 0.0605856 secs] [Times: user=0.06 sys=0.27, real=0.06 secs] 
	执行结束!共生成对象次数:10354
从日志我们可以看处本次运行总共产生对象的次数为10353，总共触发11次GC，其中FGC两次其余均为YGC

    Heap
	 PSYoungGen      total 996352K, used 302151K [0x000000076b900000, 0x00000007c0000000, 0x00000007c0000000)
	  eden space 807936K, 14% used [0x000000076b900000,0x0000000772814448,0x000000079ce00000)
	  from space 188416K, 99% used [0x000000079ce00000,0x00000007a85fd7c8,0x00000007a8600000)
	  to   space 251904K, 0% used [0x00000007b0a00000,0x00000007b0a00000,0x00000007c0000000)
	ParOldGen       total 492032K, used 366236K [0x00000006c2a00000, 0x00000006e0a80000, 0x000000076b900000)
	  object space 492032K, 74% used [0x00000006c2a00000,0x00000006d8fa72a0,0x00000006e0a80000)
	Metaspace       used 3435K, capacity 4500K, committed 4864K, reserved 1056768K
	  class space    used 372K, capacity 388K, committed 512K, reserved 1048576K
再看到程序执行完成后、JVM关闭前的日志

	1. PSYoungGen：年轻代总共为996352K，使用了302151K
		其中eden区总共占了807936K，使用了14%
		from区总共占188416K，使用99%
		to区总共占251904K，使用0%


----------
**指定堆内存的大小**
> 启动参数加上  -Xms512m -Xmx512m

输出日志：

    2021-01-22T16:54:08.940+0800: [GC (Allocation Failure) [PSYoungGen: 131584K->21486K(153088K)] 131584K->44415K(502784K), 0.0084792 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
	2021-01-22T16:54:08.969+0800: [GC (Allocation Failure) [PSYoungGen: 153070K->21494K(153088K)] 175999K->83497K(502784K), 0.0108305 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
	2021-01-22T16:54:09.002+0800: [GC (Allocation Failure) [PSYoungGen: 153078K->21490K(153088K)] 215081K->126227K(502784K), 0.0096702 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
	中间日志太多省略部分..........
	2021-01-22T16:54:09.830+0800: [GC (Allocation Failure) --[PSYoungGen: 116479K->116479K(116736K)] 439655K->466164K(466432K), 0.0082622 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
	2021-01-22T16:54:09.839+0800: [Full GC (Ergonomics) [PSYoungGen: 116479K->0K(116736K)] [ParOldGen: 349684K->327814K(349696K)] 466164K->327814K(466432K), [Metaspace: 3768K->3768K(1056768K)], 0.0420745 secs] [Times: user=0.20 sys=0.00, real=0.04 secs] 
	执行结束!共生成对象次数:9988

根据日志我们可以看到指定堆内存的大小之后生成对象的次数只有9988次

----------
## **Serial收集器** ##


> 添加启动参数：-XX:+UseSerialGC，使用Serial回收器

    2021-01-22T17:10:00.469+0800: [GC (Allocation Failure) [DefNew: 139586K->139586K(157248K), 0.0000136 secs][Tenured: 319754K->340876K(349568K), 0.0409153 secs] 459341K->340876K(506816K), [Metaspace: 3429K->3429K(1056768K)], 0.0409609 secs] [Times: user=0.03 sys=0.00, real=0.04 secs] 
	2021-01-22T17:10:00.534+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000121 secs][Tenured: 340876K->347176K(349568K), 0.0465000 secs] 480652K->347176K(506816K), [Metaspace: 3429K->3429K(1056768K)], 0.0465569 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
	2021-01-22T17:10:00.606+0800: [GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000172 secs][Tenured: 347176K->349276K(349568K), 0.0661144 secs] 486952K->357498K(506816K), [Metaspace: 3433K->3433K(1056768K)], 0.0661670 secs] [Times: user=0.08 sys=0.00, real=0.07 secs] 
	2021-01-22T17:10:00.713+0800: [Full GC (Allocation Failure) [Tenured: 349476K->348868K(349568K), 0.0691354 secs] 506671K->348868K(506816K), [Metaspace: 3769K->3769K(1056768K)], 0.0691684 secs] [Times: user=0.08 sys=0.00, real=0.07 secs] 
	执行结束!共生成对象次数:9511

在使用Serial收集器之后生成对新的次数更少了仅有9511次

    数据分析：

	[GC (Allocation Failure) [DefNew: 139776K->139776K(157248K), 0.0000172 secs][Tenured: 347176K->349276K(349568K), 0.0661144 secs] 486952K->357498K(506816K), [Metaspace: 3433K->3433K(1056768K)], 0.0661670 secs] [Times: user=0.08 sys=0.00, real=0.07 secs] 
	
	DefNew: 139776K->139776K(157248K)
	DefNew – 表示垃圾收集器的名称。这个名字表示：年轻代使用的单线程、标记-复制、STW 垃圾收集器
	139776K->139776K(157248K)：139776K为新生代被回收之前的使用容量，139776K指的是回收之后新生代被回收后目前在使用的容量
	157248K为年轻代总容量
	0.0000172为回收耗时，单位是秒
	Tenured: 347176K->349276K(349568K)：老年代回收前使用了347176K，回收后仍有349276K在使用，(349568K)老年代总容量
	0.0661144 secs:本次老年代回收总耗时
	
    
**总结**

   >  Serial收集器为串行GC,串行 GC 对年轻代使用 mark-copy（标记-复制） 算法，对老年代使用 mark-sweep-compact（标记-清除-整理）算法。 两者都是单线程的垃圾收集器，不能进行并行处理，所以都会触发全线暂停（STW），停止所有的应用线程。因此这种 GC 算法不能充分利用多核 CPU。不管有多少 CPU 内核，JVM 在垃圾收集时都只能使用单个核心。CPU 利用率高，暂停时间长。简单粗暴，就像老式的电脑，动不动就卡死。
   该选项只适合几百 MB 堆内存的 JVM，而且是单核 CPU 时比较有用
																																																																																					
----------

## **Parallel收集器** ##
**修改启动参数-XX:+UseSerialGC为 -XX:+UseParallelGC -XX:+UseParallelOldGC ，使用并行GC**

> 并行垃圾收集器对年轻代使用【标记-复制】算法，对老年代使用【标记-清除-整理】算法，年轻代和老年代的垃圾回收时都会触发STW事件，暂停所有的应用线程，再执行垃圾回收。在执行【标记】和【复制/整理】阶段都会使用多线程进行，这样可以提升标记的速度

    2021-01-22T17:50:00.458+0800: [Full GC (Ergonomics) [PSYoungGen: 50804K->0K(116736K)] [ParOldGen: 333225K->319699K(349696K)] 384029K->319699K(466432K), [Metaspace: 3595K->3595K(1056768K)], 0.0479245 secs] [Times: user=0.20 sys=0.00, real=0.05 secs] 
	2021-01-22T17:50:00.521+0800: [GC (Allocation Failure) [PSYoungGen: 58758K->18931K(116736K)] 378457K->338631K(466432K), 0.0041215 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T17:50:00.539+0800: [GC (Allocation Failure) [PSYoungGen: 77811K->17791K(116736K)] 397511K->355215K(466432K), 0.0058716 secs] [Times: user=0.08 sys=0.02, real=0.01 secs] 
	2021-01-22T17:50:00.545+0800: [Full GC (Ergonomics) [PSYoungGen: 17791K->0K(116736K)] [ParOldGen: 337423K->329790K(349696K)] 355215K->329790K(466432K), [Metaspace: 3762K->3762K(1056768K)], 0.0420404 secs] [Times: user=0.17 sys=0.00, real=0.04 secs] 
	执行结束!共生成对象次数:9225
日志解读

    PSYoungGen – 垃圾收集器的名称。这个名字表示的是在年轻代中使用的：并行的  标记-复制(mark-copy)，全线暂停(STW) 垃圾收集器
   
> 总结：并行垃圾收集器使用于多核服务器，其主要目标是增加系统吞吐量，为了达到这个目标会尽可能使用CPU的资源，在GC事件执行期间，所有 CPU 内核都在并行地清理垃圾，所以暂停时间相对来说更短；在两次GC事件中间的间隔期，不会启动GC线程，所以这段时间内不会消耗任何系统资源
> 
> 另一方面,因为并行GC的所有阶段不会暂停，所以并行GC可能会出现长时间的暂停卡顿，长时间卡顿的意思就是并行GC启动后，一次性完成gc的标记复制或者标记清除整理工作，假如系统对延迟不能容忍则不应该选择并行垃圾收集器



----------
## **CMS收集器** ##

    CMS也可以称为【并发标记清除垃圾收集器】。它的设计目标是避免老年代GC的长时间暂停，默认情况下CMS使用并发线程数等于CPU内核数的4/1

**年轻代日志分析**

    2021-01-22T20:31:11.569+0800: [ [ParNew: 157248K->17471K(157248K), 0.0271011 secs] 371219K->271592K(506816K), 0.0271280 secs] [Times: user=0.14 sys=0.03, real=0.03 secs] 

> GC (Allocation Failure)：用于区分Minor GC还是Full GC的标志；
> Allocation Failure表示触发的原因是因为内存分配失败；
> 
> ParNew: 157248K->17471K(157248K)-ParNew表示垃圾收集器的名称，对应的就是前面日志中打印的  `-XX:+UseParNewGC` 这个命令标志；表示在年轻代中使用【标记-复制】垃圾收集器，cms只负责老年代的回收
> 
> 0.0271011 secs：表示年轻代垃圾回收的耗时，时间单位是秒
> 
> 371219K->271592K(506816K), 0.0271280 secs：表示GC前后堆内存的使用情况，506816K堆内存总量，371219K回收前的使用量，收回后的使用量371219K；0.0271280 secs表示本次本次回收回收耗时
> 
> 年轻代的使用量率[157248K/157248K=100%]   堆内存使用率[371219K/506816K=73%] 老年代使用率[(371219K-157248K)/(506816K-157248K)=213971/349568=61%]   从年轻代提升到了老年代的为：[139777k-99627K=40150]
> 
> 由此可计算出老年代的容量为[506816K-157248K=349568]


----------

**Full GC日志分析**

    2021-01-22T20:31:11.720+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 337008K(349568K)] 355178K(506816K), 0.0001232 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T20:31:11.720+0800: [CMS-concurrent-mark-start]
	2021-01-22T20:31:11.722+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T20:31:11.722+0800: [CMS-concurrent-preclean-start]
	2021-01-22T20:31:11.723+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T20:31:11.723+0800: [CMS-concurrent-abortable-preclean-start]
	2021-01-22T20:31:11.723+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T20:31:11.723+0800: [GC (CMS Final Remark) [YG occupancy: 38996 K (157248 K)][Rescan (parallel) , 0.0002670 secs][weak refs processing, 0.0000076 secs][class unloading, 0.0003522 secs][scrub symbol table, 0.0005134 secs][scrub string table, 0.0001169 secs][1 CMS-remark: 337008K(349568K)] 376004K(506816K), 0.0013404 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T20:31:11.724+0800: [CMS-concurrent-sweep-start]
	2021-01-22T20:31:11.725+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	2021-01-22T20:31:11.725+0800: [CMS-concurrent-reset-start]
	2021-01-22T20:31:11.726+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 

**第1阶段 初始标记阶段**

> 这个阶段伴随着STW暂停。初始标记的目标是标记所有的根对象，包括GC ROOT直接引用的对象，以及被年轻代中所有存活对象所引用的对象。后面这部分也非常重要，因为老年代是独立回收的

    2021-01-22T20:31:11.720+0800: [GC (CMS Initial Mark) [1 CMS-initial-mark: 337008K(349568K)] 355178K(506816K), 0.0001232 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	CMS Initial Mark：初始标记，标记所有的GC ROOT
	CMS-initial-mark: 337008K(349568K):337008K指的是老年代的使用量，349568K指的是老年代总容量
	355178K(506816K), 0.0001232 secs：指的是当前堆的使用量和剩余和总容量，0.0001232表示本次标记耗时0.12毫秒时间比较短
	[Times: user=0.00 sys=0.00, real=0.00 secs] -初始标记时间暂停时间，real=0.00 secs可以看到是忽略不计的


**第2阶段 并发标记阶段**

> 在并发标记阶段，CMS从前一阶段初始标记找的到GC ROOT开始算起，遍历老年代并标记所有存活对象。

    2021-01-22T20:31:11.722+0800: [CMS-concurrent-mark: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	CMS-concurrent-mark-表明为CMS收集器的并发标记阶段
	0.002/0.002 secs：表示该阶段持续时间，分别是GC线程消耗的时间和时间消耗的时间
	
**第3阶段 并发预清理阶段**

> 此阶段同样是与应用线程并发执行，不需要暂停应用线程

    2021-01-22T20:31:11.723+0800: [CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
	CMS-concurrent-preclean: -表明这一步为并发预清理阶段，这个阶段会统计前面的并发标记阶段执行过程中发生了引用改变的对象
	 0.001/0.001 secs：表示这个阶段的持续时间，分别是GC线程运行时间和实际占用时间
	
**第4阶段 可取消的并发预清理阶段**

> 此阶段不需要暂停线程，尝试在会触发STW的Final Remark阶段开之前，尽可能多干一些活

    2021-01-22T20:31:11.723+0800: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	CMS-concurrent-abortable-preclean：表明该阶段为可取消的并发预清理阶段
	0.000/0.000 secs：表明该阶段的持续时间

**第5阶段 最终标记阶段**

> 最终标记是CMS中第二次，也是最后一次需要STW时间的阶段，本阶段会标记老年代中所有存活对象，因为之前的预清理阶段是并发执行的， 有可能GC线程跟不上程序的修改速度，所以需要进行一次暂停处理各种复杂情况；通常CMS会尝试在年轻代尽可能空的情况下执行最终标记，避免连续触发STW而导致暂停时间过长

    2021-01-22T20:31:11.723+0800: [GC (CMS Final Remark) [YG occupancy: 38996 K (157248 K)][Rescan (parallel) , 0.0002670 secs][weak refs processing, 0.0000076 secs][class unloading, 0.0003522 secs][scrub symbol table, 0.0005134 secs][scrub string table, 0.0001169 secs][1 CMS-remark: 337008K(349568K)] 376004K(506816K), 0.0013404 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	YG occupancy: 38996 K (157248 K：当前年轻代的使用量和总容量
	[Rescan (parallel) , 0.0002670 secs]：表示在程序暂停后重新扫描，已完成存活对象的标记，这部分是并发执行的，消耗时间为0.0002670 secs
	[weak refs processing, 0.0000076 secs]：第一个子阶段;处理弱引用的时间
	[class unloading, 0.0003522 secs]：第二个子阶段；卸载不是用的类，以及持续时间
	[scrub symbol table, 0.0005134 secs]：第三个子阶段；清理符号表，即持有class级别metaspace的符号表
	[scrub string table, 0.0001169 secs]：第四个子阶段；清理内联字符串对应的 string table
	[1 CMS-remark: 337008K(349568K)]：此阶段完成后老年代的使用容量和总容量
	376004K(506816K), 0.0013404 secs：GC事件持续时间

五个阶段完成后老年代所有的存活对象都会被标记，接下来JVM会将所有不使用的对象进行清除，回收老年代的空间


**第6阶段 并发清除阶段**
> 此阶段将会与应用并发执行，不需要STW，目的是删除没有被标记不再使用的对象，并收回空间

    2021-01-22T20:31:11.725+0800: [CMS-concurrent-sweep: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
	CMS-concurrent-sweep：表明为并发清除阶段
	0.001/0.001 secs：表示该阶段的持续时间和实际占用时间

**第7阶段 并发重置阶段**

> 此阶段与应用并发执行，重置CMS算法相关的内部数据结构，下一次触发GC时就可以直接使用。

    2021-01-22T20:31:11.726+0800: [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 


----------


## **G1收集器** ##

G1收集器纯年轻代回收步骤


    当堆内存的总体使用比例达到一定数值时，就会触发并发标记。 这个默认比例是  45%，但也可以通过JVM参数  InitiatingHeapOccupancyPercent 来设置。和CMS一样，G1的并发标记也是由多个阶段组成，其中一些阶段是完全并发的，还有一些阶段则会暂停应用线程。


**阶段 1: Initial Mark(初始标记)**

**阶段 2: Root Region Scan(Root区扫描)**

**阶段 3: Concurrent Mark(并发标记)**

**阶段 4: Remark(再次标记)**

**阶段 5: Cleanup(清理)**



----------
 

**Full GC (Allocation Failure)**


    G1是一款自适应的增量垃圾收集器。一般来说，只有在内存严重不足的情况下才会发生Full GC。 比如堆空
	间不足或者to-space空间不足


> 由于时间和经验有限本次总结并没有很深入，还需要长时间多次去练习理解，任重道远，老师常说：种一棵树最好的时间是十年前，其次是现在