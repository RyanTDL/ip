public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description.substring(9));
        this.by = by;
    }

    @Override
    public String getStatus() {
        return "[D]" + super.getStatus() + " (by: " + by + ")";
    }
}
