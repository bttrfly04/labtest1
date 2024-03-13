import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.PrintWriter;

public class EmployeeSalaryProcessor {
    public static void main(String[] args) {
        String inputFile = "C:\\Users\\abdra\\OneDrive\\Documents\\employeeSalaries.txt";
        String outputFile = "C:\\Users\\abdra\\OneDrive\\Documents\\employeeData.txt";

        List<Employee> employees = readEmployees(inputFile);
        writeEmployees(outputFile, employees);

        Employee topEmployee = employees.stream()
                .max(Comparator.comparing(employee -> employee.salary + (employee.salary * 0.05 * employee.yearsOfService)))
                .orElse(null);

        Employee leastEmployee = employees.stream()
                .min(Comparator.comparingInt(Employee::getYearsOfService))
                .orElse(null);

        System.out.println("Top Performing Employee: " + topEmployee.name + ", Annual Salary: " + (topEmployee.salary + (topEmployee.salary * 0.05 * topEmployee.yearsOfService)));
        System.out.println("Employee with Least Years of Service: " + leastEmployee.name + ", Years of Service: " + leastEmployee.yearsOfService);
    }

    private static List<Employee> readEmployees(String inputFile) {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] employeeData = line.split("\\|");
                if (employeeData.length != 3) {
                    System.out.println("Your Employee Data: " + line);
                    continue;
                }
                String name = employeeData[0].trim();
                double salary = Double.parseDouble(employeeData[1].trim());
                int yearsOfService = Integer.parseInt(employeeData[2].trim());
                employees.add(new Employee(name, salary, yearsOfService));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }

        return employees;
    }

    private static void writeEmployees(String outputFile, List<Employee> employees) {
        try (PrintWriter pW = new PrintWriter(new FileWriter(outputFile))) {
            employees.stream()
                    .map(employee -> employee.name + "," + (employee.salary + (employee.salary * 0.05 * employee.yearsOfService)) + "," + employee.yearsOfService)
                    .forEach(pW::println);
        } catch (IOException e) {
            System.out.println("Problem: " + e.getMessage());
        }
    }
}

class Employee {
    String name;
    double salary;
    int yearsOfService;

    public Employee(String name, double salary, int yearsOfService) {
        this.name = name;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
    }

    public int getYearsOfService() {
        return yearsOfService;
    }
}