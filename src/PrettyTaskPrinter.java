import java.util.List;

public class PrettyTaskPrinter {
    public void printTask(Task task) {
        System.out.println("--------------------------------------");
        print(task);
        System.out.println("--------------------------------------");
    }

    public void printTasks(List<Task> tasks) {
        System.out.println("--------------------------------------");

        int counter = 1;
        for (Task task : tasks) {
            System.out.print(counter + " ");
            print(task);
            counter++;
        }

        System.out.println("--------------------------------------");
    }

    private void print(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getName())
                .append(" ")
                .append(task.getPriority())
                .append(" \"")
                .append(returnValidLengthDescription(task.getDescription()))
                .append("\" ")
                .append(task.isDone());
        System.out.println(sb);
    }

    private String returnValidLengthDescription(String description) {
        if (description.length() > 64 ) {
            return description.substring(0, 64) + "...";
        } else {
            return description;
        }
    }
}


