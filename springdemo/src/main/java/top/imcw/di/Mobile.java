package top.imcw.di;

public class Mobile {
    private String brand;

    private App app;


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void runApp() {
        System.out.print(brand + "手机");
        this.app.run();
    }
}
