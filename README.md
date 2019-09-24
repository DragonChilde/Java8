# Lamabda #

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

Lambda表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符

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

- 上联：左右遇一括号省
- 下联：左侧推断类型省
- 横批：能省则省

Lambda 表达式需要“函数式接口”的支持

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

1. 调用Collections.sort()方法,通过定制排序比较两个Employee(先按年龄比,年龄相同按姓名比)，使用Lamabda作为参数传递
2. 
	1. 声明函数式接口,接口中声明抽象方法，public String getValue(String str);
	2. 声明类TestLamabda,类中编写方法使用接口作为参数,将一个字符串转成大写,并作为方法的返回值.
	3. 再将一个字符串的第2个和第4个索引位置进行截取子串
3. 
	1. 声明一个带两个泛型的函数式接口,泛型类型为<T,R> T为参数,R为返回值
	2. 接口中声明对应抽象方法
	3. 在TestLambda类中声明方法,使用接口作为参数,计算两个long型参数的和
	4. 再计算两个long型参数的乘积	