import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        sudokuSolver sd = new sudokuSolver();
        sd.solve();
        System.out.println("Sudoku answer: ");
        sd.print();

        System.out.println("Please input the capacity for the Knapsack:");

        int capacity = scanner.nextInt();
        Knapsack kp = new Knapsack(capacity);
        kp.solve();
    }
}
