import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Knapsack {
    private int[] profits;
    private int[] weights;
    private int capacity;

    /**
     * Reading from file, parse weight and profit from it
     * @param capacity capacity of knapsack problem
     * @throws FileNotFoundException if the file is not found
     */
    public Knapsack(int capacity) throws FileNotFoundException {
        Scanner sc = new Scanner((new BufferedReader((new FileReader("./src/Knapsack.txt")))));
        String[] line = sc.nextLine().trim().split(" ");
        this.capacity = capacity;
        this.profits = new int[line.length];
        this.weights = new int[line.length];

        for (int i = 0; i < line.length; i++){
            profits[i] = Integer.parseInt(line[i]);
        }

        line = sc.nextLine().trim().split(" ");
        for (int j = 0; j < line.length; j++){
            weights[j] = Integer.parseInt(line[j]);
        }
    }

    /**
     * Branch and bound
     */
    public void solve(){
        int maxProfit = 0;
        int level = -1;
        Node node1 = new Node(0, level, 0);
        Queue queue = new Queue();
        queue.enqueue(node1);

        while(!queue.isEmpty()){
            Node out = queue.peek();
            queue.dequeue();
            System.out.println("Parent node: Weight(" + out.weight + "), Profit(" + out.profit + "), Maximum Profit(" + maxProfit + "), Bound(" + bound(out) + "), level(" + out.level + ")");

            level = out.level + 1;
            int weight = out.weight + weights[level];// left child node's weight = parent node's weight + child node's weight
            int profit = out.profit + profits[level];// left child node's profit = parent node's profit + child node's profit
            Node in = new Node(weight, level, profit);// left child node

            if(weight <= capacity && profit > maxProfit){// find the maximum profit
                maxProfit = profit;
            }
            if(bound(in) > maxProfit){//if the bound of left child is larger than max profit
                queue.enqueue(in);//enqueue this node
                System.out.println("Left child node: Weight(" + in.weight + "), Profit(" + in.profit + "), Maximum Profit(" + maxProfit + "), Bound(" + bound(in) + "), level(" + in.level + ")");
            }

            weight = out.weight;//right child node have same property as parent
            profit = out.profit;
            Node in2 = new Node(weight, level, profit);
            if(bound(in2) > maxProfit){//if the bound of right child is larger than max profit
                queue.enqueue(in2);//enqueue this node
                System.out.println("Right child node: Weight(" + in2.weight + "), Profit(" + in2.profit + "), Maximum Profit(" + maxProfit + "), Bound(" + bound(in2) + "), level(" + out.level + ")");
            }
            System.out.println("----------------------------------------------------");
        }

        System.out.println("Maximum profit of this knapsack problem is: " + maxProfit);
    }

    /**
     * measuring the bound of the node from its level
     * @param in node
     * @return bound
     */
    private float bound(Node in) {
        int i = 0, j = 0;
        int totalWeight;
        float result;

        if(in.weight >= capacity){
            return 0;
        }
        else{
            result = in.profit;
            j = in.level + 1;
            totalWeight = in.weight;
            while (j < profits.length && totalWeight + weights[j] <= capacity){//calculating the bound from the input node level to the other level until the total weight plus next level's weight is full
                totalWeight = totalWeight + weights[j];
                result += profits[j];
                j++;
            }
        }
        i = j;

        if(i < profits.length){//the capacity no full yet, still have room from part of the item to fit in
            result += (float)((capacity - totalWeight) * (profits[i]/weights[i]));//fraction of the item
        }
        return result;
    }

    static class Node{
        private int weight;
        private int level;
        private int profit;
        private Node next;

        public Node(int weight, int level, int profit){
            this.weight = weight;
            this.level = level;
            this.profit = profit;
        }

        public void setNext(Node node){
            next = node;
        }

        public Node getNext(){
            return next;
        }
    }

}
