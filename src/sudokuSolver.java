import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class sudokuSolver {
    private int[][] sdk;

    /**
     * Initialize the sudoku broad from reading the text file
     * @throws FileNotFoundException if the file is not found
     */
    public sudokuSolver() throws FileNotFoundException {
        sdk = new int[9][9];
        Scanner sc = new Scanner((new BufferedReader((new FileReader("./src/test.txt")))));
        String[] line = sc.nextLine().trim().split(" ");

        for(int i = 0; i < sdk.length; i++){
            for(int j = 0; j < sdk.length; j++){
                sdk[i][j] = Integer.parseInt(line[j]);
            }
            if(sc.hasNext()){
                line = sc.nextLine().trim().split(" ");
            }
        }
    }

    /**
     * Try to fit 1 to 9 to the sudoku using recursion, if the value is not valid on the broad, then go back to last one to try another value
     * @return True if valid
     */
    public boolean solve(){
        int[] emptySpace = findEmpty();

        if(emptySpace[0] == -1 && emptySpace[1] == -1){
            return true;
        }
        else{
            for(int i = 1; i <= 9; i++){
                if(checkValid(emptySpace[0], emptySpace[1], i)){
                    sdk[emptySpace[0]][emptySpace[1]] = i;
                    if(solve()){
                        return true;
                    }
                    sdk[emptySpace[0]][emptySpace[1]] = 0;
                }
            }
        }
        return false;
    }

    /**
     * Print the final sudoku result
     */
    public void print(){
        for(int i = 0; i < sdk.length; i++){
            for(int j = 0; j < sdk.length; j++){
                System.out.print(sdk[i][j] + "|\t");
            }
            System.out.print("\n---------------------------------\n");
        }
    }

    /**
     * Checking on which node is empty
     * @return the position of the node
     */
    public int[] findEmpty(){
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        for(int i = 0; i < sdk.length; i++){
            for(int j = 0; j < sdk.length; j++){
                if(sdk[i][j] == 0){
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * checking if this number can be fit into the node
     * @param i y-axis of that node
     * @param j x-axis of that node
     * @param num 1 to 9
     * @return true if that is valid
     */
    public boolean checkValid(int i, int j, int num){
        int x = j / 3;
        int y = i / 3;

        //checking row
        for(int q = 0; q < sdk.length; q++){
            if(q == i){
                continue;
            }
            else {
                if(num == sdk[q][j]) {
                    return false;
                }
            }
        }
        //checking column
        for(int k = 0; k < sdk.length; k++){
            if(k == j){
                continue;
            }
            else {
                if(num == sdk[i][k]) {
                    return false;
                }
            }
        }

        //checking 3*3 of sudoku
        for(int o = y * 3; o < y * 3 + 3; o++){
            for(int q = x * 3; q < x * 3 + 3; q++){
                if(j == o && i == q){
                    continue;
                }
                else {
                    if(num == sdk[o][q]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
