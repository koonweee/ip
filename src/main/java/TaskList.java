import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TaskList {
    //abstract the arraylist access and parsing of file
    private ArrayList<Task> taskList;

    TaskList(File file) throws IOException {
        this.loadTaskFile(file);
    }

    /**
     * Loads task file into task list
     * @param file Storage file
     * @throws IOException exception thrown when reading storage file
     */
    public void loadTaskFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        this.taskList = new ArrayList<>();
        while (line != null) {
            String[] split = line.split("%d%");
            if (split.length > 3) {
                this.taskList.add(Task.createTask(split[0], split[1], split[2], split[3]));
            } else {
                this.taskList.add(Task.createTask(split[0], split[1], split[2], ""));
            }
            line = br.readLine();
        }
    }

    /**
     * Sets task as done
     * @param doneTask index of task
     */
    public void done(int doneTask) {
        taskList.get(doneTask).setDone();
    }

    /**
     * Deletes task
     * @param deleteTask index of task
     */
    public void delete(int deleteTask) {
        taskList.remove(deleteTask);
    }

    /**
     * Adds a deadline task
     * @param task Task name
     * @param deadline Task deadline
     * @return Task object
     */
    public Task addDeadline(String task, String deadline) {
        Deadline dl = new Deadline(task, deadline);
        taskList.add(dl);
        return dl;
    }

    /**
     * Adds a todo task
     * @param task Task name
     * @return Task object
     */
    public Task addTodo(String task) {
        Todo td = new Todo(task);
        taskList.add(td);
        return td;
    }

    /**
     * Adds a event task
     * @param task Task name
     * @param at Task date
     * @return Task object
     */
    public Task addEvent(String task, String at) {
        Event e = new Event(task, at);
        taskList.add(e);
        return e;
    }

    public String get(int doneTask) {
        return taskList.get(doneTask).toString();
    }

    public String taskCount() {
        return "Now you have " + taskList.size() + " tasks in the list.";
    }

    @Override
    public String toString() {
        String rtn = taskList.stream().map(task -> task.toString()).collect(Collectors.joining("\n"));
        if (!rtn.equals("")) {
            String[] rtnSplit = rtn.split("\n");
            for (int i = 0; i < rtnSplit.length; i++) {
                rtnSplit[i] = (i + 1) + ". " + rtnSplit[i];
            }
            return String.join("\n", Arrays.asList(rtnSplit));
        } else {
            return "";
        }
    }

    /**
     * Finds tasks matching a given key
     * @param key Key to be found
     * @return List of tasks found
     */
    public String find(String key) {
        String rtn = taskList.stream()
                .map(task -> task.toString())
                .filter(task -> task.contains(key))
                .collect(Collectors.joining("\n"));
        String[] rtnSplit = rtn.split("\n");
        for (int i = 0; i < rtnSplit.length; i++) {
            rtnSplit[i] = (i + 1) + ". " + rtnSplit[i];
        }
        return String.join("\n", Arrays.asList(rtnSplit));
    }

    public ArrayList<Task> getTasks() {
        return this.taskList;
    }
}
