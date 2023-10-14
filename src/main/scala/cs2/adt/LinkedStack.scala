package cs2.adt

import java.util.EmptyStackException

class LinkedStack[A] extends Stack[A] {
  private class Node(val data:A, var next:Node)
  private var head:Node = null
  private var len = 0

  def push(elem:A):Unit = {
    head = new Node(elem, head)
    len += 1
  }
  def pop():A = {
    if(isEmpty) throw new EmptyStackException()
    val tmp = head.data
    head = head.next
    len -= 1
    tmp
  }
  def peek():A = {
    if(isEmpty) throw new EmptyStackException()
    head.data
  }
  def isEmpty():Boolean = { head == null }
  def length():Int = { len }
  def clear():Unit = {
    head = null
  }
}