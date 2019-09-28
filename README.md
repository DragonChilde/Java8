# Lambda表达式 #

**为什么使用Lambda表达式**

Lambda是一个匿名函数,我们可以把Lambda表达式理解为是一段可以传递的代码(将代码像数据一样进行传递)。可以写出更简洁、更灵活的代码。作为一种更紧凑的代码风格.使Java的语言表达能力得到了提升

下面代码进行对比

	public class TestLamabda1 {
	
	    public static void main(String[] args) {
	        test1();
	    }
	
	    //原来的匿名内部类
	    public static void test1(){
	        //方法一
	        Comparator<String> comparator = new Comparator<String>() {
	            @Override
	            public int compare(String o1, String o2) {
	                return Integer.compare(o1.length(), o2.length());
	            }
	        };
	        TreeSet<String> tree1 = new TreeSet<>(comparator);
	
	
	        //方法二
	        TreeSet<String> tree2 = new TreeSet<>(new Comparator<String>() {
	            @Override
	            public int compare(String o1, String o2) {
	                return Integer.compare(o1.length(), o2.length());
	            }
	        });
	    }
	
	    //现在的 Lambda 表达式
	    public static void test2(){
	        Comparator<String> com = (x, y) -> Integer.compare(x.length(), y.length());
	        TreeSet<String> ts = new TreeSet<>(com);
	    }	
	}

对比可以看出原来的匿名内部类所需的代码量比较多

再举个例子:新增一个Employee类根据不同需求取出结果

	public class TestLambda2 {
	   static List<Employee> emps = Arrays.asList(
	            new Employee(101, "张三", 18, 9999.99),
	            new Employee(102, "李四", 59, 6666.66),
	            new Employee(103, "王五", 28, 3333.33),
	            new Employee(104, "赵六", 8, 7777.77),
	            new Employee(105, "田七", 38, 5555.55)
	    );
	
	    public static void main(String[] args) {
	        List<Employee> employees1 = filterEmployeeForAge(emps);
	        System.out.println(employees1);
	        System.out.println("============");
	        List<Employee> employees2 = filterEmployeeForSalary(emps);
	        System.out.println(employees2);
	    }
	
	    //需求1：获取公司中年龄小于 35 的员工信息
	    private static List<Employee> filterEmployeeForAge(List<Employee> emps){
	        ArrayList<Employee> list = new ArrayList<>();
	        for(Employee employee:emps){
	            if (employee.getAge()<35){
	                list.add(employee);
	            }
	        }
	        return list;
	    }
	
	    //需求2：获取公司中工资大于 5000 的员工信息
	    private static List<Employee> filterEmployeeForSalary(List<Employee> emps)
	    {
	        ArrayList<Employee> list = new ArrayList<>();
	        for(Employee employee:emps){
	            if (employee.getSalary()>5000){
	                list.add(employee);
	            }
	        }
	        return list;
	    }
	}

如果每次根据不同需求新增方法过滤不同的结果，其代码的重复量比较多,实际的就只是判断的方式不同

- 优化方式一：策略设计模式

		//定义接口
		public interface MyPredicate<T> {
		    boolean filter(T t);
		}

		//根据要过滤的需求新增两个实现类
		public class FilterEmployeeForAge implements MyPredicate<Employee> {
		    @Override
		    public boolean filter(Employee employee) {
		        if (employee.getAge()<35){
		            return true;
		        }
		        return false;
		    }
		}

		public class FilterEmployeeForSalary implements MyPredicate<Employee> {
		    @Override
		    public boolean filter(Employee employee) {
		        if (employee.getSalary()>5000){
		            return true;
		        }
		        return false;
		    }
		}

		//在实际调用中新增方法把参数进去
		private static List<Employee> filterEmployee1(List<Employee> emps, MyPredicate<Employee> filter)
	    {
	        List<Employee> list = new ArrayList<>();
	        for (Employee employee:emps){
	            if(filter.filter(employee))
	            {
	                list.add(employee);
	            }
	        }
	
	        return list;
	    }

- 优化方式二:匿名内部类

		//根据上面的方式只定义接口和方法，具体实现在内部类里实现
		private static List<Employee> filterEmployee2(List<Employee> emps)
	    {
	
	        List<Employee> list = new ArrayList<>();
	
	        list = filterEmployee1(emps, new MyPredicate<Employee>() {
	            @Override
	            public boolean filter(Employee employee) {
	               if (employee.getSalary() >5000){
	                   return true;
	               }
	               return false;
	            }
	        });
	        return list;
	    }

- 优化方式三:Lambda 表达式

		//根据优化一里定义的接口和方法，用Lambda 表达式
	    private static void filterEmployee3(List<Employee> emps)
	    {
	        List<Employee> employees1 = filterEmployee1(emps, (e) -> e.getAge() < 35);
	        employees1.forEach(System.out::println);
	        System.out.println("=======================");
	        List<Employee> employees2 = filterEmployee1(emps, (e) -> e.getSalary() > 5000);
	        employees2.forEach(System.out::println);
	    }

- 优化方式四：Stream API

	    private static void filterEmployee4(List<Employee> emps){
	        emps.stream().filter((e)->e.getSalary()>5000).forEach(System.out::println);
	        System.out.println("==================");
	        emps.stream().filter((e)->e.getAge()<35).forEach(System.out::println);
	    }

Lambda表达式的基础语法：Java8中引入了一个新的操作符 "**->**" 该操作符称为箭头操作符或 Lambda 操作符

箭头操作符将 Lambda 表达式拆分成两部分：
 
- 左侧：Lambda 表达式的参数列表
- 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体

**语法格式一：无参数，无返回值() -> System.out.println("Hello Lambda!");**

	/**Lambda表达式相关于实现接口类的方法**/
	private static void test1()
    {
        int num = 0;//jdk 1.7 前，必须是 final

		//注意:内部类在传递局部变量在1.7前必须是final属性的,因此不能变更,
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello world!"+num);
            }
        };
        runnable1.run();

		//因此，Lambda表达式也一样在传局部变量时,相当于final属性
        Runnable runnable2 = () -> System.out.println("Hello world!");
        runnable2.run();

    }


**语法格式二：有一个参数，并且无返回值(x) -> System.out.println(x)**

	 private static void test2()
    {
		//相当于实现了接口Consumer.accept()方法
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("hello world!");

    }


**语法格式三：若只有一个参数，小括号可以省略不写x -> System.out.println(x)**

	  private static void test2()
    {
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("hello world!");

    }

**语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句**

	 private static void test3()
    {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("函数式接口");
            return Integer.compare(x, y);
        };
    }

**语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写**

	  private static void test4()
    {
        Comparator<Integer> com = (x , y) -> Integer.compare(x , y);

    }

**语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”**

	  private static void test4()
    {
		//这里的类型可以忽略不写，就像下面的定义数组和方法一样
        Comparator<Integer> com = (Integer x , Integer y) -> Integer.compare(x , y);
    }

	 private static void test5()
    {
		//数组后面的字符串不需要定义类型，JVM会推断出
        String[] strs = {"aaa","bbb","ccc"};

		//后ArrayList不需要定义类型
        List<String> list = new ArrayList<>();

		//方法的调用不需要写具体的类型
        show(new HashMap<>());
    }

    private static void show(Map<String,Integer> map){

    }

类型推断:上述Lambda表达式中的参数类型都是由编译器推断得出的.Lambda表达式中无需指定类型，程序依然可以编译,这是因为javac根据程序的上下文,在后台推断出了参数的类型.Lambda表达式的类型依赖于上下文环境，是由编译器推断出来的。这就是所谓的"类型推断"

- 上联：左右遇一括号省
- 下联：左侧推断类型省
- 横批：能省则省

Lambda 表达式需要“函数式接口”的支持

# 函数式接口 #

什么是函数式接口

- 只包含一个抽象方法的接口,称为**函数式接口**
- 你可以通过Lambda表达式来创建该接口的对象(若Lambda表达式抛出一个受检异常,那么该异常需要在目标接口的抽象方法上进行声明)
- 我们可以在任意函数式接口上使用**@FunctionalInterface**注解,这样做可以检查它是否是一个函数式接口,同时javadoc也会包含一条声明，说明这个接口是一个函数式接口

**函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解@FunctionalInterface 修饰可以检查是否是函数式接口**

	@FunctionalInterface
	public interface MyFun {
	
	    public Integer getValue(Integer num);
	    
		//当定义多了一个方法时会提示错误
	   //public Integer getNum(Integer num);
	}

需求：对一个数进行运算

	 private static void test6()
    {
        Integer num = operation(100, x -> x * x);
        System.out.println(num);

        System.out.println(operation(100, x -> 200 + x));

    }

    private static Integer operation(Integer num, MyFun myFun)
    {
        return myFun.getValue(num);
    }

-  调用Collections.sort()方法,通过定制排序比较两个Employee(先按年龄比,年龄相同按姓名比)，使用Lamabda作为参数传递

		static List<Employee> emps = Arrays.asList(
	            new Employee(101, "张三", 18, 9999.99),
	            new Employee(102, "李四", 59, 6666.66),
	            new Employee(103, "王五", 28, 3333.33),
	            new Employee(104, "赵六", 8, 7777.77),
	            new Employee(105, "田七", 38, 5555.55)
	    );

		private static void test1(){
	        Collections.sort(emps,(e1,e2) -> {
	            if(e1.getAge() == e2.getAge())
	            {
	                return e1.getName().compareTo(e2.getName());
	            }
	            return Integer.compare(e1.getAge(),e2.getAge());
	        });
	
	        for (Employee employee:emps)
	        {
	            System.out.println(employee);
	        }
	    }

		Employee{id=104, name='赵六', age=8, salary=7777.77}
		Employee{id=101, name='张三', age=18, salary=9999.99}
		Employee{id=103, name='王五', age=28, salary=3333.33}
		Employee{id=105, name='田七', age=38, salary=5555.55}
		Employee{id=102, name='李四', age=59, salary=6666.66}

2. 
	1. 声明函数式接口,接口中声明抽象方法，public String getValue(String str);
	2. 声明类TestLamabda,类中编写方法使用接口作为参数,将一个字符串转成大写,并作为方法的返回值.
	3. 再将一个字符串的第2个和第4个索引位置进行截取子串


			@FunctionalInterface
			public interface MyFunction {
			
			    public String getValue(String str);
			}

			   private static void test2()
			    {
			        String str = show("Hello World", x -> x.toUpperCase());
			        System.out.println(str);
			        String substr = show("Hello World", x -> x.substring(2, 4));
			        System.out.println(substr);
			    }
			
			    private static String show(String str, MyFunction myFunction){
			        return myFunction.getValue(str);
			    }

3. 
	1. 声明一个带两个泛型的函数式接口,泛型类型为<T,R> T为参数,R为返回值
	2. 接口中声明对应抽象方法
	3. 在TestLambda类中声明方法,使用接口作为参数,计算两个long型参数的和
	4. 再计算两个long型参数的乘积	

			@FunctionalInterface
			public interface MyFunction2<T,R> {
			    public R getValue(T value1,T value2);
			}

		    private static void test3()
		    {
		        operation(100L,200L,(x,y) -> x+y);
		        operation(300L,5L,(x,y) -> x*y);
		    }
		
		    private static void operation(Long l1, Long l2, MyFunction2<Long,Long> myFunction2){
		        System.out.println(myFunction2.getValue(l1, l2));
		    }

**作为参数传递Lambda表达式：为了将Lambda表达式作为参数传递,接收Lambda表达式的参数类型必须是与该Lambda表达式兼容的函数式接口的类型**

**Java8 内置的四大核心函数式接口**

- **Consumer<T> : 消费型接口 void accept(T t) 对类型为T的对象应用操作**

		 private static void test1()
	    {
	        show("hello world!",x -> System.out.println(x));
	    }
	
	    private static void show(String str, Consumer<String> consumer)
	    {
	        consumer.accept(str);
	    }

- **Supplier<T> : 供给型接口 T get() 返回类型为T的对象**

		 private static void test2()
	    {
	        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
	        System.out.println(numList);
	    }
	
	    private static List<Integer> getNumList(Integer num, Supplier<Integer> supplier)
	    {
	        List<Integer> list = new ArrayList<>();
	        for (int i = 0; i < num; i++) {
	            Integer integer = supplier.get();
	            list.add(integer);
	        }
	        return list;
	
	    }

- **Function<T, R> : 函数型接口 R apply(T t) 对类型为T的对象应用操作,并返回结果.结果是R类型的对象**

	 	 private static void test3()
	    {
	        String s1 = handleStr("\t\t\t hello world!   ", x -> x.trim());
	        System.out.println(s1);
	
	        String s2 = handleStr("hello world!", x -> x.substring(2,6));
	        System.out.println(s2);
	    }
	
	    private static String handleStr(String str , Function<String,String> function)
	    {
	        return function.apply(str);
	    }

- **Predicate<T> : 断言型接口 boolean test(T t) 确定类型为T的对象是否满足某约束,并返回boolean值**

	    private static void test4()
	    {
	        List<String> list = Arrays.asList("aaa", "bbbb", "cccccc", "ddddddd");
	        List<String> stringList = filterStr(list, x -> x.length() > 4);
	        System.out.println(stringList);
	    }
	
	    private static List<String> filterStr(List<String> list, Predicate<String> predicate)
	    {
	        List<String> strList = new ArrayList<>();
	
	        for (String str : list) {
	            if(predicate.test(str)){
	                strList.add(str);
	            }
	        }
	        return strList;
	
	    }

其它接口
	
	BiFunction<T,U,R> 	params:T,U	return：R	//对类型为T,U参数应用操作,返回R类型的结果.包含方法为R apply<T t,U u>

	//Function子接口
	UnaryOperator<T> 	params:T	return T	//对类型为T的对象进行一元运算,并返回T类型的结果.包含方法为T apply(T t)

	//BiFunction子接口
	BinaryOperator<T>	params:T T	return T	//对类型为T的对象进行二元运算,并返回T类型的结果.包含方法为 T apply(T t1,T t2);

	BiConsumer<T,U>		params:T,U	return void	//对类型为T,U参数应用操作.包含方法为void accept(T t,U u)

	ToIntFunction<T>|ToLongFunction<T>|ToDoubleFunction<T>|		params:T	return int、long、doublue 	//分别计算为int\long\double值的函数

	IntFunction<R>|LongFunction<R>|DoubleFunction<R> 	params：int|long|double	return：R	//参数分别int\long\double类型的函数

# 方法引用和构造器引用 #

当要传递给Lambda体的操作,已经有实现的方法了，可以使用方法引用！(实现抽象方法的参数列表，必须与方法引用的参数列表保持一致！)

方法引用:使用操作符"**::**"将方法名和对象 类的名字分隔开来

一、 方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用 （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）

- **对象的引用 :: 实例方法名**

		/**System.out::println()方法的参数和返回类型必须与函数式接口Consumer的方法accept一样才可以这样使用**/
		public static void test1()
	    {
			/*新使用*/
	        PrintStream printStream = System.out;
	        Consumer<String> consumer = (str) -> printStream.println(str);
	        consumer.accept("hello world!");

	        System.out.println("==========");

	        Consumer<String> consumer2 = printStream::println;
	        consumer2.accept("hello java8");

			/*原有使用*/
	        Consumer<String> consumer3 = System.out::println;
	    }

- **对象的引用 :: 实例方法名**

		/**Employee.getName()方法的参数和返回类型与Supplier.get()一致**/
		public static void test2()
	    {
	        Employee employee = new Employee(101, "李四", 18, 9999.99);
	
	        /*原有使用*/
	        Supplier<String> sup = () -> employee.getName();
	        System.out.println(sup.get());
	
	        System.out.println("=============");
	
	        /*新使用*/
	        Supplier<String> sup2 = employee::getName;
	        System.out.println(sup2.get());
	    }

- **对象的引用 :: 实例方法名**

			/**double max(double a, double b)与R apply(T t, U u)一致**/
	 	 public static void test3()
	    {
			/*原有使用*/
	        BiFunction<Double,Double,Double> biFunction = (x, y) -> Math.max(x, y);
	        System.out.println(biFunction.apply(1.5, 22.2));
	
	        System.out.println("==============");

			/*新使用*/
	        BiFunction<Double, Double, Double> biFunction2 = Math::max;
	        System.out.println(biFunction.apply(1.2, 1.5));
	    }

- **类名 :: 静态方法名**


		/**Comparator int compare(T o1, T o2) |Integer static int compare(int x, int y)**/
		 public static void test4()
	    {
			/*原有使用*/
	        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
	        System.out.println(comparator.compare(1, 2));
	
	        System.out.println("===========");
	
			/*新使用*/
	        Comparator<Integer> comparator2 = Integer::compare;
	        System.out.println(comparator2.compare(3, 4));
	
	    }

- **类名 :: 实例方法名**

	    public static void test5()
	    {
			/*原有使用*/
	        BiPredicate<String,String> biPredicate1 = (x, y) -> x.equals(y);
	        System.out.println(biPredicate1.test("abc", "abc"));
	
	        System.out.println("=============");
	
			/*新使用*/
	        BiPredicate<String, String> biPredicate2 = String::equals;
	        System.out.println(biPredicate2.test("efg", "efg"));
	
	        System.out.println("=============");
	
			/*原有使用*/
	        Function<Employee,String> function1 = (e) -> e.show();
	        System.out.println(function1.apply(new Employee()));
	
	        System.out.println("===============");
	
			/*新使用*/
	        Function<Employee, String> function2 = Employee::show;
	        System.out.println(function2.apply(new Employee()));
	    }

**注意：**

1. **方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！**
2. **若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName**

二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！

格式:**ClassName::new**

与函数式接口相结合,自动与函数式接口中方法兼容.可以把构造器引用赋值给定义的方法,与构造器参数列表要与接口中抽象方法的参数列表一致

-  **类名 :: new**

		/**构造函数的参数必须与函数式接口的参数一致**/
	    public static void test6()
	    {
			 /*新使用*/
	        Supplier<Employee> supplier1 = () -> new Employee();
	        System.out.println(supplier1.get());
	
	        System.out.println("===============");
	
			 /*新使用*/
	        Supplier<Employee> supplier2 = Employee::new;
	        System.out.println(supplier2.get());
	    }

		
		 public static void test7()
	    {
			/*新使用*/
	        Function<String, Employee> function = Employee::new;
	
			/*新使用*/
	        BiFunction<String, Integer, Employee> biFunction = Employee::new;
	    }


三、 **数组引用 type[]::new**

- 类型[] :: new;

		public static void test8()
	    {
			/*原有使用*/
	        Function<Integer,String[]> function1 = (length) -> new String[length];
	        String[] strs = function1.apply(10);
	        System.out.println(strs.length);
	        System.out.println("================");
	
			/*新使用*/
	        Function<Integer,Employee[]> function2 = Employee[]::new;
	        Employee[] employees = function2.apply(20);
	        System.out.println(employees.length);
	    }

# Stream API #

Java8中有两大最为重要的改变.第一个是Lambda;另外一个则是Stream Api(java.util.strea.*)

Stream是Java8中处理集合的关键抽象概念,它可以指定你希望对集合进行的操作,可以执行非常复杂的查找、过滤和映射数据等操作.使用Stream API对集合数据进行操作,就类拟于使用SQL执行的数据库查询.也可以使用Stream API来并行执行操作.简而言之,Stream API提供了一种高效且易于使用的处理数据的方式

什么是Stream?流(Stream)到底是什么 

是数据渠道,用于操作数据源(集合、数组等)所生成的元素序列.“**集合讲的是数据，流讲的是计算**”

**注意:**

1. Stream自己不会存储元素
2. Stream不会改变源对象.相反，他们会返回一个持有结果的新Stream
3. Stream操作是延迟执行的.这意味着它们会等到需要结果的时候才执行

**Stream API 的操作步骤**

1. **创建 Stream**：一个数据源(如：集合、数组)，获取一个流
2. **中间操作**:一个中间操作链,对数据源的数据进行处理
3. **终止操作(终端操作)**:一个终止操作,执行中间操作链,并产生结果

**创建Stream有以下几种方式**

1. Java8中的Collection接中被扩展,提供了两个获取流的方法:

		default Stream<E> stream()	//返回一个顺序流
		default Stream<E> parallelStream()	//返回一个并行流
2. **由数组创建流**Java8中的Arrays的静态方法stream()可以获取数组流:

		static <T> Stream<T> stream(T[] array)	//返回一个流
		
		//重载形式,能够处理对应基本类型的数组:
		public static IntStream stream(int[] array)
		public static LongStream stream(long[] array)
		public static DoubleStream stream(double[] array)
3. **由值创建流**可以使用静态方法Stream.of(),通过显示值创建一个流.它可以接收任意数量的参数。

		public static<T> Stream<T> of(T...values)	//返回一个流

4. **由函数创建流:创建无限流**可以使用静态方法Stream.iterate()和Stream.generate(),创建无限流

		//迭代
		public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
		//生成
		public static<T> Stream<T> generate(Supplier<T> s)

实际操作

	private static void test1()
    {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();      //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream();      //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] integers = new Integer[10];
        Stream<Integer> integerStream = Arrays.stream(integers);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> strem2 = Stream.of(1, 2, 3, 4, 5, 6, 7);

        //4. 创建无限流
        Stream<Integer> stream3 = Stream.iterate(0, x -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> doubleStream = Stream.generate(Math::random).limit(10);
        doubleStream.forEach(System.out::println);
    }

**Stream的中间操作**

多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作,否则中间操作不会执行任何的处理！而在终止操作时一次性全部处理,称为"惰性求值"

- **筛选与切片**

		filter(Predicate p)		//接收Lambda,从流中排除某些元素
		distinct()				//筛选，通过流所生成元素的hashCode()和equals()去除重复元素
		limit(long maxSize)		//截断流,使其元素不超过给定数量
		skip(long n)			//跳过元素，返回一个扔掉了前n个元素的流.若流中元素不足n个，则返回一个空流.与limit(n)互补

例

	 //内部迭代：迭代操作 Stream API 内部完成
    private static void test2()
    {
        //所有的中间操作不会做任何的处理(这里不会有任何输出打印)
        Stream<Employee> stream = emps.stream().filter((e) -> {
            System.out.println("测试中间操作");
            return e.getAge() <= 35;
        });
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);
    }

		/*
		测试中间操作
		测试中间操作
		Employee{id=101, name='张三', age=18, salary=9999.99}
		测试中间操作
		Employee{id=103, name='王五', age=28, salary=3333.33}
		测试中间操作
		Employee{id=104, name='赵六', age=8, salary=7777.77}
		测试中间操作
		Employee{id=104, name='赵六', age=8, salary=7777.77}
		测试中间操作
		Employee{id=104, name='赵六', age=8, salary=7777.77}
		测试中间操作
		*/

	 //外部迭代
    private static void test3()
    {
        Iterator<Employee> employeeIterator = emps.iterator();
        while (employeeIterator.hasNext())
        {
            System.out.println(employeeIterator.next());
        }
    }

	 private static void test4()
    {
        emps.stream().filter((e) -> {
            System.out.println("短路!");
            return e.getSalary() >= 5000;
        }).limit(3).forEach(System.out::println);
    }
	/*
	短路!
	Employee{id=102, name='李四', age=59, salary=6666.66}
	短路!
	Employee{id=101, name='张三', age=18, salary=9999.99}
	短路!
	短路!
	Employee{id=104, name='赵六', age=8, salary=7777.77}
	*/

	 private static void test5()
    {
        emps.parallelStream().filter(e -> e.getSalary() >= 5000).skip(2).forEach(System.out::println);
    }
	/*
	Employee{id=104, name='赵六', age=8, salary=7777.77}
	Employee{id=105, name='田七', age=38, salary=5555.55}
	Employee{id=104, name='赵六', age=8, salary=7777.77}
	Employee{id=104, name='赵六', age=8, salary=7777.77}
	*/

- **映射**

		map(Function f)		//接收一个函数作为参数,该函数会被应用到每个元素上,并将其映射成一个新的元素
		mapToDouble(ToDoubleFunction f)		//接收一个函数作为参数,该函数会被应用到每个元素上,产生一个新的DoubleStream
		mapToInt(ToIntFunction f)		//接收一个函数作为参数,该函数会被应用到每个元素上,产生一个新的IntStream
		mapToLong(ToLongFunction f)		//接收一个函数作为参数,该函数会被应用到每个元素上，产生一个新的LongStream
		flatMap(Function f)		//接收一个函数作为参数,将流中的每个值都换成另一个流,然后把所有流连接成一个流

例:

		private static void test7()
	    {
	        Stream<String> stringStream = emps.stream().map(e -> e.getName());
	        stringStream.forEach(System.out::println);
	
			/*
			李四
			张三
			王五
			赵六
			赵六
			赵六
			田七
			*/
	        System.out.println("===========");
	
	        List<String> stringList = Arrays.asList("aaa", "bbb", "ccc", "ddd");
	        Stream<String> stream = stringList.stream().map(String::toUpperCase);
	        stream.forEach(System.out::println);
			/*
			AAA
			BBB
			CCC
			DDD
			*/
	
	        System.out.println("=============");
	        Stream<Stream<Character>> strem2 = stringList.stream().map(TestStreamApi::filterCharacter);
	        strem2.forEach(sm -> sm.forEach(System.out::println));
			/*
			a
			a
			a
			b
			b
			b
			c
			c
			c
			d
			d
			d
			*/
	
	        System.out.println("===========");
	        Stream<Character> strem3 = stringList.stream().flatMap(TestStreamApi::filterCharacter);
	        strem3.forEach(System.out::println);
			/*
			a
			a
			a
			b
			b
			b
			c
			c
			c
			d
			d
			d
			*/
	    }
	
	    private static Stream<Character> filterCharacter(String str)
	    {
	        List<Character> list = new ArrayList<>();
	
	        for(Character chr:str.toCharArray())
	        {
	            list.add(chr);
	        }
	        return list.stream();
	    }

**注意:map()和flatMap()的使用相当于集合的add()和addAll()操作一样**

map()是把流中的各个集合元素传到新的流中,而flatMap()是把流中集合里各个元素传到新的流里,所以map()返回的是Stream<Stream<Object>>,而flatMap()直接是Stream<Object>

	 private static void test8()
    {
        List<String> stringList = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        ArrayList<Object> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add(stringList);
        System.out.println(list);
        ArrayList<Object> list2 = new ArrayList<>();
        list2.add("11");
        list2.add("22");
        list2.addAll(stringList);
        System.out.println(list2);

		//add()集合中还有集合,addAll()集合中只有各个元素
		/*
		[11, 22, [aaa, bbb, ccc, ddd]]
		[11, 22, aaa, bbb, ccc, ddd]
		*/
    }

- **排序**

		sorted()	//产生一个新流，其中按自然顺序排序
		sorted(Comparator comp)		//产生一个新流,其中按比较器顺序排序

	   	/*
		sorted()——自然排序(Comparable)：按调用对象的实现好的compareTo()排序方法进行排序(例如：String)
		sorted(Comparator com)——定制排序(Comparator):按自定义的方法进行排序
		*/
	    private static void test9()
	    {
	        List<String> stringList = Arrays.asList("aaa", "ddd", "bbb", "ccc");
	        stringList.stream().sorted().forEach(System.out::println);
	
	        System.out.println("==============");
	
	        emps.stream().sorted((e1,e2) -> {
	            if(e1.getAge() == e2.getAge())
	            {
	                return e1.getName().compareTo(e2.getName());
	            }
	            return Integer.compare(e1.getAge(),e2.getAge());
	        }).forEach(System.out::println);
	    }

**Stream终止操作**

终端操作会从流的流水线生成结果.其结果可以是任何不是流的值,例如:List、Integer,甚至是void

- 查找与匹配

		allMatch(Predicate p)		//检查是否匹配所有元素
		anyMatch(Predicate p)		//检查是否至少匹配一个元素
		noneMatch(Predicate p)		//检查是否没有匹配所有元素
		findFirst()				//返回第一个元素
		findAny()				//返回当前流中的任意元素
		count()					//返回流中元素总数
		max(Comparator c)		//返回流中最大值
		min(Comparator c)		//返回流中最小值
		forEach(Consumer c)		//内部迭代(Collection接口需要用户去做迭代,称为外部迭代.相反,Stream API使用内部迭代--它帮你把迭代做了)

**allMatch\anyMatch\noneMatch**

	  private static List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Status.BUSY)
    	);

		private static void test1()
	    {
	        boolean b1 = emps.stream().allMatch(e -> e.getStatus().equals(Status.BUSY));
	        System.out.println(b1);
	        System.out.println("=============");
	        boolean b2 = emps.stream().anyMatch(e -> e.getStatus().equals(Status.BUSY));
	        System.out.println(b2);
	        System.out.println("===============");
	        boolean b3 = emps.stream().noneMatch(e -> e.getStatus().equals(Status.BUSY));
	        System.out.println(b3);
	        System.out.println("===============");
	    }
		/*
		false
		=============
		true
		===============
		false
		===============
		*/

		 private static void test2()
	    {
			//如果Double前加-是取反，取最大值
	        Optional<Employee> op1 = emps.stream().sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())).findFirst();
	        System.out.println(op1);
	        System.out.println("==============");
	
			//这里使用的是并行流,因此是多条线程同时取最快唯一值
	        Optional<Employee> op2 = emps.parallelStream().filter(e -> e.getStatus().equals(Status.FREE)).findAny();
	        System.out.println(op2);
	    }

		/*
		Optional[Employee{id=103, name='王五', age=28, salary=3333.33, status=VOCATION}]
		==============
		Optional[Employee{id=101, name='张三', age=18, salary=9999.99, status=FREE}]
		*/

	   private static void test3()
    	{	
			//计算个数
	        long count = emps.stream().filter(e -> e.getStatus().equals(Status.FREE)).count();
	        System.out.println(count);
	
	        System.out.println("=================");
	
	        Optional<Double> max = emps.stream().map(Employee::getSalary).max(Double::compare);
	        System.out.println(max);
	
	        System.out.println();
	
			//如果Double前加-是取反，取最大值
	        Optional<Employee> min = emps.stream().min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
	        System.out.println(min);
			
	    }
		/*
		3
		=================
		Optional[9999.99]
		============
		Optional[Employee{id=103, name='王五', age=28, salary=3333.33, status=VOCATION}]
		*/

- 归约

		reduce(T iden, BinaryOperator b)		//可以将流中元素反复结合起来,得到一个值.返回T
		reduce(BinaryOperator b)			//可以将流中元素反复结合起来,得到一个值.返回Optional<T>
		/**备注::map和reduce的连接通常称为map-reduce模式,因Google用它来进行网络搜索而出名**/

		private static void test1()
	    {
	        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7,8,9,10);
			//第一次把0放到x，数组值1放到y，计算完把结果放到,值2放到y......进行累加
	        Integer reduce = list.stream().reduce(0, (x, y) -> x + y);
	        System.out.println(reduce);
	
	        System.out.println("===============");

	        Optional<Double> op = emps.stream().map(Employee::getSalary).reduce(Double::sum);
	        System.out.println(op.get());

			/**注意:上面的返回类型不是Optional,是因为在reduce已经设置了初始值为0，结果是不会为Null值的,而下面的对象统计,因为对象有可能为空，固会以返回Optional类型处理Null值**/
	    }
		/*
		55
		===============
		48888.84000000001
		*/


- 收集

		collect(Collector c)		//将流转换为其他形式.接收一个Collector接口的实现,用于给Stream中元素做汇总的方法
		
		/**Collector接口中方法的实现决定了如何对流执行收集操作(如收集到List、Set、Map).但是Collectors实用类提供了很多静态方法,可以方便地创建常见收集器实例**/

		toList	List<T>		//把流中元素收集到List
		toSet	Set<T>		//把流中元素收集到Set
		toCollection	Collection<T>	把流中元素收集到创建的集合
	    private static void test3()
	    {
	        List<String> list = emps.stream().map(Employee::getName).collect(Collectors.toList());
	        list.forEach(System.out::println);
	
	        System.out.println("============");
	
			//如果对象值有重复可以用Set去从
	        Set<String> set = emps.stream().map(Employee::getName).collect(Collectors.toSet());
	        set.forEach(System.out::println);
	
	        System.out.println("==============");
	
	        HashSet<String> hashSet = emps.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
	        hashSet.forEach(System.out::println);
	    }

		counting	Long		//计算流中元素的个数
		summingInt	Integer		//对流中元素的整数属性求和
		averagingInt	Double	//计算流中元素Integer属性的平均值
		summarizingInt		IntSummaryStatistics		//收集流中Integer属性的统计值.如:平均值
		joining		String		//连接流中每个字符串
		maxBy		Optional<T>		//根据比较器选择最大值
		minBy		Optional<T>		//根据比较器选择最小值
		 private static void test4()
	    {
			//maxBy
	        Optional<Double> max = emps.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compareTo));
	        System.out.println(max.get());
	
	        System.out.println("==============");
		
			//minBy
	        Optional<Employee> op = emps.stream().collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
	        System.out.println(op.get());
	
	        System.out.println("================");
	
			//summingDouble
	        Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSalary));
	        System.out.println(sum);
	
	        System.out.println("================");
	
			//averagingDouble
	        Double avg = emps.stream().collect(Collectors.averagingDouble(Employee::getSalary));
	        System.out.println(avg);
	
	        System.out.println("==================");
	
			//counting
	        Long count = emps.stream().collect(Collectors.counting());
	        System.out.println(count);
	
	        System.out.println("==============");
	
			//summarizingDouble
	        DoubleSummaryStatistics dss = emps.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
	        System.out.println(dss.getMax());
	    }

		groupingBy		Map<K,List<T>>		//根据某属性值对流分组,属性为K,结果为V
	    //分组
	    private static void test5()
	    {
	        Map<Status, List<Employee>> map = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
	        System.out.println(map);
	    }
	
	    //多级分组(可以按多个属性无限级分下去)
	    private static void test6()
	    {
	        Map<Status, Map<String, List<Employee>>> map = emps.stream().collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
	            if (e.getAge() >= 60) {
	                return "老年";
	            } else if (e.getAge() >= 35) {
	                return "中年";
	            } else {
	                return "成年";
	            }
	        })));
	        System.out.println(map);
	    }
		
		partitioningBy		Map<Boolean,List<T>>		//根据true或false进行分区
		 //分区
	    private static void test7()
	    {
	        Map<Boolean, List<Employee>> map = emps.stream().collect(Collectors.partitioningBy(e -> e.getSalary()>= 5000));
	        System.out.println(map);
	    }

		joining		//把对象里的字符串取出进行拼接
		 private static void test8()
	    {
	        String collect = emps.stream().map(Employee::getName).collect(Collectors.joining(",", "---", "==="));
	        System.out.println(collect);
	    }
		/**---李四,张三,王五,赵六,赵六,赵六,田七===**/

		reducing	归约产生的类型		//从一个作为累加器的初始值开始,利用BinaryOperator与流中元素逐个结合,从而归约成单个值
		 private static void test9()
	    {
	        Optional<Double> sum = emps.stream().map(Employee::getSalary).collect(Collectors.reducing(Double::sum));
	        System.out.println(sum);
	    }
		/**利用这方法也可以得到上面summingDouble之类所有方法的效果**/

		collectiogAndThen		转换函数返回的类型		//包裹另一个收集器,对其结果转换函数