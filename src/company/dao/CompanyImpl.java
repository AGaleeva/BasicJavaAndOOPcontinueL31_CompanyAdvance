package company.dao;

import company.model.Employee;
import company.model.SalesManager;

import java.util.function.Predicate;

public class CompanyImpl implements Company {

    private Employee[] employees;
    private int size; // текущее кол-во сотрудников

    public CompanyImpl(int capacity) {
        employees = new Employee[capacity];
    }

    @Override
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.length == size || findEmployee(employee.getId()) != null) {
            return false;
        }
        employees[size++] = employee;
        return true;
    }

    @Override
    public Employee removeEmployee(int id) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getId() == id) {
                Employee victim = employees[i];
                employees[i] = employees[--size]; // здесь мы не сохраняем порядок добавления в массиве, поэтому просто ставим последнего на место удаляемого
                employees[size] = null;
                return victim;
            }
        }
        return null;
    }

    @Override
    public Employee findEmployee(int id) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getId() == id) {
                return employees[i];
            }
        }
        return null;
    }

    @Override
    public int quantity() {
        return size;
    }

    @Override
    public double totalSalary() {
        double sum = 0;
        for (int i = 0; i < size; i++) {
            sum += employees[i].calcSalary();
        }
        return sum;
    }

 /*   @Override
    public double avgSalary() {     перешло в дефолтный метод
        return totalSalary() / size;
    }*/

    @Override
    public double totalSales() {
        double sum = 0;
        for (int i = 0; i < size; i++) {
            if (employees[i] instanceof SalesManager) {
                SalesManager sm = (SalesManager) employees[i];
                sum += sm.getSalesValue();
            }
        }
        return sum;
    }

    @Override
    public void printEmployees() {
        for (int i = 0; i < size; i++) {
            System.out.println(employees[i]);
        }
    }

    @Override
    public Employee[] findEmployeesHoursGreeterThan(int hours) {

//        Predicate<Employee> predicate = (Employee employee) -> {return employee.getHours() >= hours};
        Predicate<Employee> predicate = e -> e.getHours() >= hours; // e = employee
        return findEmployeesByPredicate(predicate);
    }


    @Override
    public Employee[] findEnployeesSalaryRange(int minSalary, int maxSalary) {
        Predicate<Employee> predicate = new Predicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.calcSalary() >= minSalary && employee.calcSalary() < maxSalary;
            }
        };
        return findEmployeesByPredicate(predicate);
    }


    private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(employees[i])) {
                count++;
            }
        }
        Employee[] res = new Employee[count];
        for (int i = 0, j = 0; j < res.length; i++) {
            if (predicate.test(employees[i])) {
                res[j] = employees[i];
                j++;
            }
        }
        return res;
    }
}

// employees[i].calcSalary() >= minSalary && employees[i].calcSalary() < maxSalary



