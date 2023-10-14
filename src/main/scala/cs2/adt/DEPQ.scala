package cs2.adt

class DEPQ[A <% Ordered[A]] extends DoubleEndPriorityQueue[A] {
  //Place your implementation here
  private class Node(var data:A, var next:Node)
  private var head:Node = null
  private var minVal:Node = head
  private var beforeMin:Node = head

  //Return true if there are no elements in the DEPQ
  def isEmpty():Boolean = { head == null }

  //Add an element to the DEPQ
  def add(elem: A):Unit = {
    var rover = head
    if(head == null || elem > head.data) {
      head = new Node(elem, head)
      rover = head
    } else {
      while(rover.next != null && rover.next.data > elem) {
        rover = rover.next
      }
      rover.next = new Node(elem, rover.next)
      rover = rover.next
    }
    if(rover.next == null){
      minVal = rover
    }
  }

  //Return the largest element in the DEPQ
  def peekMax():A = { head.data }

  //Return AND Remove the largest element from the DEPQ
  def max():A = {
    val ret = head.data
    head = head.next
    ret
  }

  //Return the smallest element in the DEPQ
  def peekMin():A = { minVal.data }

  //Return AND Remove the smallest element from the DEPQ
  def min():A = {
    val ret = minVal.data
    var rover = head
    if(rover.next == null){
      head = null
      minVal = head
    } else {
      while(rover.next.next != null){
        rover = rover.next
      }
      rover.next = null
      minVal = rover
    }
    ret
  }
}