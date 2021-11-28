public class Knapsack {
    private int[] profits;
    private int[] weights;
    private int capacity;
    private int[] fractionProfits;

    public Knapsack(){
        this.capacity = 16;
        this.profits = new int[]{40, 30, 50, 10};
        this.weights = new int[]{2, 5, 10, 5};
    }

    public void solve(){
        int maxProfit = 0;
        int level = -1;
        Node node1 = new Node(0, level, 0);
        Queue queue = new Queue();
        queue.enqueue(node1);

        while(!queue.isEmpty()){
            Node out = queue.peek();
            queue.dequeue();

            level = out.level + 1;
            int weight = out.weight + weights[level];
            int profit = out.profit + profits[level];
            Node in = new Node(weight, level, profit);

            if(weight <= capacity && profit > maxProfit){
                maxProfit = profit;
            }
            if(bound(in) > maxProfit){
                queue.enqueue(in);
            }

            weight = out.weight;
            profit = out.profit;
            Node in2 = new Node(weight, level, profit);
            if(bound(in2) > maxProfit){
                queue.enqueue(in2);
            }
        }

        System.out.println(maxProfit);
    }

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
            while (j < profits.length && totalWeight + weights[j] <= capacity){
                totalWeight = totalWeight + weights[j];
                result += profits[j];
                j++;
            }
        }
        i = j;

        if(i < profits.length){
            result += (capacity - totalWeight) * (profits[i]/weights[i]);
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
