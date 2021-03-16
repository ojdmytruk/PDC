package task1;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        Create create = new Create(2.0, 0.0, "Uniform");
        Process process = new Process(6.0, 0.0, "Uniform", 2, 10);
        Model model = new Model(process, create, 10000.0);
        model.simulation();
    }


}
