package com.techdisqus.interview;

import java.util.Stack;

public class FindKthNoInLinkedList {

    public static void main(String[] args) {

        LinkedList<Integer> list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.printKthNode(4);
        list.printKthNodeV1(4,list.head);
        list.printKthNodeV2(4);

       // list.display();


    }

    private static class LinkedList<T>{

        private Node<T> head;
        private Node<T> last;

        public void display(){
            Node temp = head;
            while (temp != null){
                System.out.println(temp.data);
                temp = temp.getNext();
            }
        }

        /**
         * Algorithm:
         * Traverse K nodes from start. If no of nodes (n) less than k (n < k) , return as there is NO more than k nodes
         * Now, move countingNode till end and currentNode to next.
         * When the countingNode reaches to end node (NULL), the current node will be pointing to K th node.
         * @param k
         */
        public void printKthNode(int k){

            if(head == null){
                return;
            }

            int currentCount = 0;

            Node countingNode = getHead();

            // traverse till kth node from first
            while (currentCount < k){
               if(countingNode == null){
                    System.out.println("not found");
                    return;
                }
                countingNode = countingNode.next;
                currentCount++;
            }

            //If I am here means that I have kth node present in list
            //Traversing counting node Till makes current node to kth node
            //for example, n =4, list contains 1,2,3,4,5
            //countingNode with point to 4
            //The below while loop, move countingNode to 5 and currentNode to 2
            //next iteration countingNode will be null and currentNode points to 2, which is the kthNode

            Node currentNode = getHead();
            while (countingNode != null){
                countingNode = countingNode.getNext();
                currentNode = currentNode.getNext();
            }
            System.out.println(k +"th node from list is "+currentNode.data);


        }

        int count = 0;

        public void printKthNodeV1(int k, Node head){

            //return as head is null
            if(head == null){
                return;
            }
            //recur to next node
            printKthNodeV1(k,head.next);
            //increment count
            count++;
            //if count is k then we have kth node.
            if(count == k){
                System.out.println("K th node is "+head.data);
            }
        }

        //using stack, requires additional space.
        public void printKthNodeV2(int k){

            if(head == null){
                return;
            }
            Stack<Node> stack = new Stack<>();

            Node temp = head;
            //push all the nodes to stack
            while (temp != null){
                stack.push(temp);
                temp = temp.next;
            }
            int count = 0;
            //pop from stack while counting
            //if count is K then print value and return.
            while (!stack.isEmpty()){
                temp = stack.pop();
                count++;
                if(count == k){
                    System.out.println("K th node is "+temp.data);
                    return;
                }
            }
        }
        public Node<T> getHead() {
            return head;
        }


        public void add(T t){

            if(head == null){
                head = new Node<T>().setData(t);
                last = head;
                return;
            }


            Node newNode = new Node().setData(t);
            last.setNext(newNode);
            last = newNode;

        }
    }

    private static class Node<T>{
        private T data;
        private Node next;


        public T getData() {
            return data;
        }

        public Node<T> setData(T data) {
            this.data = data;
            return this;
        }

        public Node getNext() {
            return next;
        }

        public Node<T> setNext(Node next) {
            this.next = next;
            return this;
        }

    }



}
