public class TaskBuilder {
    private String name;
    private String description;
    private int priority;
    private boolean done;

    public Task build() {
        return new Task(name, description, priority, done);
    }

    public TaskBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }


    public TaskBuilder setPriority(int priority) {
        if (priority < 0 || priority > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        this.priority = priority;
        return this;
    }

    public TaskBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

}
