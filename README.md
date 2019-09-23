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