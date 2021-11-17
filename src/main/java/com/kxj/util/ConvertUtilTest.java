package com.kxj.util;

import com.google.common.collect.Lists;
import com.kxj.entity.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiangjin.kong
 * @date 2021/11/17 13:55
 */
public class ConvertUtilTest {

    @Test
    public void test() {
        ArrayList<Employee> employees = Lists.newArrayList(Employee.builder().id(1).firstName("李").age(20).build(),
                Employee.builder().id(2).firstName("赵").age(32).build(),
                Employee.builder().id(3).firstName("孔").age(12).build(),
                Employee.builder().id(4).firstName("李").age(23).build());
        // java8 List转Map
        Map<Integer, Employee> map = employees.stream().collect(Collectors.toMap(Employee::getId, employee -> employee));
        System.out.println(JsonUtil.toJsonString(map));

        // 第二种，利用封装工具类，key为Id，value为实体
        // Function<Employee, Integer> function = employee -> employee.getId();
        Map<Integer, Employee> employeeMap = ConvertUtil.listToMap(employees, Employee::getId);
        System.out.println(JsonUtil.toJsonString(employeeMap));

        //  List 转 List<T>
        List<Integer> list = ConvertUtil.listToList(employees, Employee::getId);
        System.out.println(list);

        // Map转List
        Function<Map.Entry<Integer, Employee>, Employee> function = new Function<Map.Entry<Integer, Employee>, Employee>() {
            @Override
            public Employee apply(Map.Entry<Integer, Employee> integerEmployeeEntry) {
                return integerEmployeeEntry.getValue();
            }
        };
        List<Employee> employeeList = ConvertUtil.mapToList(employeeMap, function);
        System.out.println(employeeList);
    }
}
