public class Queue{
    private Knapsack.Node nextNode;
    private Knapsack.Node topNode;

    public Queue(){
        this.nextNode = null;
        this.topNode = null;
    }

    public void enqueue(Knapsack.Node node){
        if(topNode == null){
            topNode = node;
        }
        else {
            nextNode.setNext(node);
        }
        nextNode = node;
    }

    public void dequeue(){
        if(topNode == null){
            return;
        }
        topNode = topNode.getNext();

        if(topNode == null){
            nextNode = null;
        }
    }

    public Knapsack.Node peek(){
        return topNode;
    }

    public boolean isEmpty(){
        return topNode == null;
    }



}
