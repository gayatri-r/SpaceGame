package cs2.adt

import org.junit._
import org.junit.Assert._


class DequeTester {
  var d:Deque[Int] = Deque[Int]()

  @Before def init():Unit = {
    d = Deque[Int]()
  }

  @Test def checkIsEmpty():Unit = {
    assertTrue(d.isEmpty())
    d.append(13)
    assertFalse(d.isEmpty())
  }

  @Test def peekFrontBack():Unit = {
    d.append(13)
    d.append(14)
    d.append(15)
    assertTrue(d.peekFront() == 13)
    assertTrue(d.peekBack() == 15)
  }

  @Test def backTest():Unit = {
    d.prepend(13)
    d.append(14)
    assertTrue(d.back() == 14)
    d.back()
    assertTrue(d.isEmpty())
  }

  @Test def frontTest(): Unit = {
    d.prepend(13)
    d.append(14)
    assertTrue(d.front() == 13)
    d.front()
    assertTrue(d.isEmpty())
  }

  @Test def order(): Unit = {
    d.append(15)
    d.append(16)
    d.prepend(14)
    d.prepend(13)
    //should print 13, 14, 15, and 16
    while(!d.isEmpty()){
      println(d.front())
    }
  }

}