# GC日志分析 #
## jstat查看JVM的GC情况 ##

****
jstat可以查看堆各部分的使用量,以及类加载的数量

1. > jstat -options
1. jstat -class pid         # 显示ClassLoad相关信息
1. jstat -compiler pid          # 显示JIT编译的相关信息
1. jstat -gc pid                # 显示和gc相关的堆信息
1. jstat -gccapacity pid        # 显示各个代的容量以及使用情况
1. jstat -gccause pid           # 显示垃圾回收相关信息,同时显示最后一次或正在发生GC的原因
1. jstat -gcmetacapacity pid    # 显示`metaspace`的大小
1. jstat -gcnew pid             # 显示新生代信息
1. jstat -gcnewcapacity pid     # 显示新生代大小和使用情况
1. jstat -gcold pid             # 显示老年代和永久代的信息
1. jstat -gcoldcapacity pid     # 显示老年代大小
1. jstat -gcutil pid            # 显示垃圾收集信息
1. jstat -printcompilation pid  # 输出JIT编译的方法信息

----------

日志打印参数解释：

1. S0C ：堆中年轻代中第一个survivor（form区）的容量 (字节)
1. S1C：堆中年轻代中第二个survivor（to区）的容量 (字节)
2. S0U：堆新生代中form区的已使用容量 (字节)
3. S1U：堆中新生代to区的已使用容量 (字节)
4. EC：堆中年轻代中Eden的容量 (字节)
5. EU：堆中年轻代中Eden区已使用的容量 (字节)
6. OC：堆中老年代的容量（字节）
7. OU：对中老年代已使用的容量（字节）
8. MC：非堆中元数据区的容量（字节）
9. MU：非堆中元数据区已使用的容量（字节）
10. CCSC：当前压缩类空间大小（字节）
11. CCSU：当前压缩类空间已使用的容量（字节）
12. YGC：从应用程序启动到采样时年轻代中gc次数
13. YGCT：从应用程序启动到采样时年轻代中gc所用时间(s)
14. FGC ：从应用程序启动到采样时old代(全gc)gc次数
15. FGCT ：从应用程序启动到采样时old代(全gc)gc所用时间(s)
16. GCT：从应用程序启动到采样时gc用的总时间(s)


----------
## 应用堆内存日志分析 ##


**使用Serial垃圾收集器** 

启动参数及命令：java -Xmx521m -Xms512m -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -XX:+PrintGC -jar gateway-server-0.0.1-SNAPSHOT.jar

参数解释

1. Xmx1g：堆最大内存为`512m`
2. Xms1g：对最小内存为`512m`
3. -XX:-UseAdaptiveSizePolic：关闭自适应参数
4. -XX:+UseSerialGC 使用串行Serial垃圾收集器进行垃圾回收工作
5. -XX:+PrintGC：打印垃圾回收日志 

----------

使用命令`jstat -gc pid 250 50`打印出各个空间的实时使用情况

    >jstat -gc 9536 200 100
      S0      S1     E      O      M     CCS    YGC   YGCT    FGC   FGCT    CGC    CGCT    GCT
	 51.44   0.00   6.15   3.26  97.23  89.61    2    0.039    2    0.118    -      -      0.157
 
----------
	>java -Xmx521m -Xms512m -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -XX:+PrintGCDetails -jar gateway-server-0.0.1-SNAPSHOT.jar
	
    [0.908s][info   ][gc,heap        ] GC(0) DefNew: 117507K->0K(157248K)
	[0.909s][info   ][gc,heap        ] GC(0) Tenured: 0K->5235K(349568K)
	[0.922s][info   ][gc,metaspace   ] GC(0) Metaspace: 20716K->20716K(1069056K)
	[0.922s][info   ][gc             ] GC(0) Pause Full (Metadata GC Threshold) 114M->5M(495M) 69.206ms
	
	[0.908s][info   ][gc,heap        ] GC(0) DefNew: 117507K->0K(157248K)
	[0.909s][info   ][gc,heap        ] GC(0) Tenured: 0K->5235K(349568K)
	[0.922s][info   ][gc,metaspace   ] GC(0) Metaspace: 20716K->20716K(1069056K)
	[0.922s][info   ][gc             ] GC(0) Pause Full (Metadata GC Threshold) 114M->5M(495M) 69.206ms

	[0.908s][info   ][gc,heap        ] GC(0) DefNew: 117507K->0K(157248K)
	[0.909s][info   ][gc,heap        ] GC(0) Tenured: 0K->5235K(349568K)
	[0.922s][info   ][gc,metaspace   ] GC(0) Metaspace: 20716K->20716K(1069056K)
	[0.922s][info   ][gc             ] GC(0) Pause Full (Metadata GC Threshold) 114M->5M(495M) 69.206ms

	[3.246s][info   ][gc,start       ] GC(3) Pause Young (Allocation Failure)
	[3.269s][info   ][gc,heap        ] GC(3) DefNew: 139904K->8987K(157376K)
	[3.270s][info   ][gc,heap        ] GC(3) Tenured: 11393K->11393K(349568K)
	[3.277s][info   ][gc,metaspace   ] GC(3) Metaspace: 40782K->40782K(1085440K)
	[3.277s][info   ][gc             ] GC(3) Pause Young (Allocation Failure) 147M->19M(495M) 31.078ms
	[3.278s][info   ][gc,cpu         ] GC(3) User=0.08s Sys=0.02s Real=0.03s

    
> 从上面日志可以看到这次的`GC`触发`YGC`，`FGC`各两次，刚开始看日志觉得这个时候的老年代使用率并没有达到需要进行`GC`的程度（全因当年不好好学英语，没看懂`Metadata GC Threshold`,翻译过来是`metaspace`达到`C`阈值）而这次`FGC`这是由于没有对`metaspace`的空间进行设置，因此需要在启动时加上`-XX:MetaspaceSize=128m`这个参数，如果不设置`metaspace`满了之后会触发`FGC`
   
*Serial为串行收集器，目前老而无用*

----------
**使用Parallel垃圾收集器**
**使用Serial垃圾收集器** 

启动参数及命令：java -Xmx521m -Xms512m -XX:-UseAdaptiveSizePolicy -XX:+UseSerialGC -XX:+PrintGC -jar gateway-server-0.0.1-SNAPSHOT.jar
