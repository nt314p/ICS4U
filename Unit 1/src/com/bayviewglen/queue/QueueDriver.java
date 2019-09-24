package com.bayviewglen.queue;

public class QueueDriver {

	public static void main(String[] args) {
		Queue q = new IntLinkedListQueue();
		
		q.enqueue(new Integer(1));
		q.enqueue(new Integer(2));
		q.enqueue(new Integer(3));
		
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		System.out.println(q.isEmpty());
		
		Queue a = new IntLinkedListQueue();
		Queue b = new IntArrayQueue();
		a.enqueue(11);
		a.enqueue(22);
		a.enqueue(33);
		a.enqueue(44);
		a.enqueue(55);
		a.enqueue(66);
		
		System.out.println("Printing out a:");
		while (!a.isEmpty()) {
			System.out.println(a.peek());
			b.enqueue(a.dequeue());
		}
		System.out.println("Printing out b:");
		while (!b.isEmpty()) {
			System.out.println(b.dequeue());
		}
		b.clear();
		System.out.println(b.isEmpty());
	}

}
