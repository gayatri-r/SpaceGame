package cs2.adt

class TheDeque[A : Manifest] extends Deque[A] {
  private class Node(val data:A, var next:Node)
  private var head:Node = null
  private var tail:Node = null

  //should add a single element to the logical "beginning" of the Deque
  def prepend(elem: A): Unit = {
    if(isEmpty) {
      head = new Node(elem, head)
      tail = head
    }
    else {
      head = new Node(elem, head)
    }
  }

  //should add a single element to the logical "end" of the Deque
  def append(elem: A): Unit = {
    if(isEmpty){
      tail = new Node(elem, null)
      head = tail
    }
    else {
      tail.next = new Node(elem, null)
      tail = tail.next
    }
  }

  //should return AND remove a single element from the logical "beginning" of the Deque
  def front(): A = {
    val ret:A = head.data
    head = head.next
    if(head == null) {
      tail = head
    }
    ret
  }

  //should return AND remove a single element from the logical "end" of the Deque
  def back(): A = {
    val ret:A = tail.data
    var rover = head

    if(rover.next != null) {
      while(rover.next.next != null) {
        rover = rover.next
      }
      rover.next = null
      tail = rover
    }
    else {
      head = null
      tail = null
    }
    ret
  }

  //should return BUT NOT remove a single element from the logical "beginning"
  def peekFront(): A = {
    head.data
  }

  //should return BUT NOT remove a single element from the logical "end"
  def peekBack(): A = {
    tail.data
  }

  //should return true if the Deque contains no elements, and false otherwise
  def isEmpty(): Boolean = {
    head == null && tail == null
  }
  
}