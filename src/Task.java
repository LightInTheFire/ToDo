import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int priority;
    private boolean done;

    public Task(String name, String description, int priority, boolean done) {
        if (priority < 0 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", done=" + done +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 0 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;
        return priority == task.priority && done == task.done && name.equals(task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + priority;
        result = 31 * result + Boolean.hashCode(done);
        return result;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
