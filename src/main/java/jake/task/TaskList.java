package jake.task;

import java.util.ArrayList;

import jake.ui.Ui;

import static jake.common.Messages.MESSAGE_TASK_ADDED;
import static jake.common.Messages.MESSAGE_TASK_DELETED;


public class TaskList {

    private static Ui ui = new Ui();
    private static ArrayList<Task> commands = new ArrayList<>();

    public int size() {
        return commands.size();
    }

    public Task get(int index) {
        return commands.get(index);
    }

    public void listTasks() {
        ui.showListedTasks();
        for (int i = 0; i < commands.size(); i++){
            System.out.println(Integer.toString(i+1) + "." + commands.get(i));
        }
        ui.showLineString();
    }

    // Retrieve task number from user input. Used in toggleTask() & delete()
    public int retrieveTaskNumber(String userInput) {
        int taskNumber = Integer.parseInt(userInput.substring(userInput.lastIndexOf(" ")+1));
        return taskNumber;
    }

    // Mark or Unmark respective task.
    public void toggleTask(String userInput, String taskType) {
        int taskNumber = retrieveTaskNumber(userInput);
        if (taskNumber>commands.size()){
            ui.showNonexistentTask();
        } else if (taskType.equals("unmark")){
            ui.showTaskUnmarked();
            commands.get(taskNumber-1).markTask(false);
        } else {
            ui.showTaskMarked();
            commands.get(taskNumber-1).markTask(true);
        }
        ui.showLineString();
    }

    // Add tasks (Based off individual inputs)
    public void addInputtedTask(String userInput, String taskType) {
        Task newTask;
        switch (taskType) {
        case "todo":
            newTask = new ToDo(userInput);
            break;
        case "deadline":
            String[] deadlineSections = userInput.split(" by ");
            newTask = new Deadline(deadlineSections[0], deadlineSections[1]);;
            break;
        case "event":
            String[] eventSections = userInput.split(" from ");
            String[] eventTimings = eventSections[1].split(" to ");
            newTask = new Event(eventSections[0], eventTimings[0], eventTimings[1]);
            break;
        default:
            return;
        } 
        commands.add(newTask);
        System.out.printf(MESSAGE_TASK_ADDED, newTask.toString(), commands.size());
    }

    // Add tasks (Based off saved data from data.txt)
    public void addSavedTask(String userInput, char taskType) {
        Task newTask;
        String shortenedTask = userInput.substring(6);
        boolean isCompleted = userInput.charAt(4) == 'X';
        switch (taskType) {
        case 'T':
            newTask = new ToDo("todo" + shortenedTask);
            break;
        case 'D':
            // "\\" deals with PatternSyntaxException due to the (
            String[] deadlineSections = shortenedTask.split(" \\(by: "); 
            newTask = new Deadline("deadline" + deadlineSections[0], 
                    deadlineSections[1].substring(0, deadlineSections[1].length()-1));;
            break;
        case 'E':
            String[] eventSections = shortenedTask.split(" \\(from: ");
            String[] eventTimings = eventSections[1].split(" to: ");
            newTask = new Event("event" + eventSections[0], eventTimings[0], 
                    eventTimings[1].substring(0, eventTimings[1].length()-1));
            break;
        default:
            return;
        } 
        newTask.markTask(isCompleted);
        commands.add(newTask);
    }

    // Delete respective task
    public void deleteTask(String userInput) {
        int taskNumber = retrieveTaskNumber(userInput);
        try {
            System.out.printf(MESSAGE_TASK_DELETED, commands.get(taskNumber-1), commands.size()-1);
            commands.remove(taskNumber-1);
        } catch (IndexOutOfBoundsException e) {
            ui.showNonexistentTask();
        }
    }
}
