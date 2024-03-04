package jake.task;
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatus() {
        String icon = isDone ? "X" : " ";
        String status= "[" + icon + "] ";
        return status;
    }

    public void markTask(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return getStatus() + this.description;
    }
}
