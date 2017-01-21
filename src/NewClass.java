/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kyle
 */
public class NewClass {
        
    public static void main(String[]args){
        Node head = new Node(null);
        Node current = head;
        while (true){
        if(current.next== null){
            current.next = new Node("Data2");
            current = current.next;
            break;
        }else
            current = current.next;
            continue;
        }
        }
    
    
}

class Node{
    String data;
    Node next;
    //Node previous;
    
    public Node(String data){
        this.data = data;
    }
}
