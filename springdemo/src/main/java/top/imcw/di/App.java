package top.imcw.di;

public class App {

    public App(String name) {
        this.name = name;
    }

    private String name;

    public void run() {
        System.out.println(name + " run");
    }
}
