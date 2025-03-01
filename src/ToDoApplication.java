
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToDoApplication {
    private final List<Task> tasks;
    private final PrettyTaskPrinter prettyTaskPrinter;
    private final Scanner scanner;

    public ToDoApplication() {
        this.tasks = new ArrayList<>();
        this.prettyTaskPrinter = new PrettyTaskPrinter();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        loadState();
        System.out.println("Welcome to your todo list!");
        boolean flag = true;
        while(flag) {
            System.out.println("Please select an option:");
            System.out.println("""
                    1) Show tasks
                    2) Show task description
                    3) Add task
                    4) Update task
                    5) Remove task
                    6) Exit application""");

            int option = scanner.nextInt();

            switch(option) {
                case 1 -> showTasks();
                case 2 -> showTask();
                case 3 -> addTask();
                case 4 -> updateTask();
                case 5 -> removeTask();
                case 6 -> flag = false;
                default -> System.out.println("Invalid option");
            }
            saveState();
        }
    }

    private void showTasks() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks");
            return;
        }

        prettyTaskPrinter.printTasks(tasks);
    }

    private void loadState() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("tasks.txt"))) {
            Pattern pattern = Pattern.compile("Task\\{.*name=['\"](.*?)['\"].*description=['\"](.*?)['\"].*priority=(\\d+).*done=(true|false).*\\}");
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String name = matcher.group(1);
                    String description = matcher.group(2);
                    int priority = Integer.parseInt(matcher.group(3));
                    boolean done = Boolean.parseBoolean(matcher.group(4));
                    Task task = new Task(name, description, priority, done);
                    tasks.add(task);
                }
            }

        } catch (IOException e) {
            System.out.println("Error while loading tasks.txt");
        }
    }

    private void saveState() {
        try (FileWriter fileWriter = new FileWriter("tasks.txt"))  {
            for (Task task : tasks) {
                fileWriter.write(task.toString());
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error while saving tasks.txt");
        }
    }

    private void updateTask() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks to update");
            return;
        }
        System.out.println("Choose a index of a task you want to update:");
        int taskIndex = scanner.nextInt();
        try {
            Task task = tasks.get(taskIndex - 1);
            System.out.println("What you want to update?");
            System.out.println("""
                    1. Name
                    2. Description
                    3. Priority
                    4. Readiness""");
            int option = scanner.nextInt();
            switch(option) {
                case 1 -> {
                    System.out.println("Please enter new task name:");
                    task.setName(scanner.next());
                }
                case 2 -> {
                    System.out.println("Please enter new task description:");
                    task.setDescription(scanner.nextLine());
                }
                case 3 -> {
                    System.out.println("Please enter new task priority(1 to 5):");
                    try {
                        task.setPriority(scanner.nextInt());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 4 -> {
                    System.out.println("Please enter new task readiness(true or false):");
                    try {
                        task.setDone(scanner.nextBoolean());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                    }
                }
                default -> System.out.println("Invalid option");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no task with index " + taskIndex);
        }
    }

    private void showTask() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks");
            return;
        }
        System.out.println("Choose a index of a task which description you want to see:");
        int taskIndex = scanner.nextInt();
        try {
            String description = tasks.get(taskIndex - 1).getDescription();
            System.out.println(description);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no task with index " + taskIndex);
        }

    }

    private void removeTask() {
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks to remove");
            return;
        }

        System.out.println("Choose a index of a task you want to remove: ");
        int taskIndex = scanner.nextInt();
        try {
            tasks.remove(taskIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("There is no task with index " + taskIndex);
        }

    }

    private void addTask() {
        TaskBuilder taskBuilder = new TaskBuilder();

        System.out.println("Please enter the name of the task you would like to add:");
        String name = scanner.next();
        taskBuilder.setName(name);

        scanner.nextLine();
        System.out.println("Please enter the description of the task you would like to add:");
        String description = scanner.nextLine().trim();
        taskBuilder.setDescription(description);

        System.out.println("Please enter the priority of the task you would like to add (1 to 5):");
        int priority = scanner.nextInt();
        try {
            taskBuilder.setPriority(priority);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        tasks.add(taskBuilder.setDone(false).build());
        System.out.println("Task added!");
    }
}
