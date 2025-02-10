
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
    }

    private void saveState() {
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
                    task.setPriority(scanner.nextInt());
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
            System.out.println("Priority should be an integer between 1 and 5");
            return;
        }

        tasks.add(taskBuilder.setDone(false).build());
        System.out.println("Task added!");
    }
}
