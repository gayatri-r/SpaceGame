package cs2.adt

import org.junit._
import org.junit.Assert._

class DEPQTester {
  //Include your thorough tester code here, including @Begin and @Test methods
  //and any fields needed for testing
  var d:DEPQ[Int] = new DEPQ[Int]()
  @Before def init():Unit = {
    d = new DEPQ[Int]()
  }

  @Test def checkIsEmpty():Unit = {
    assertTrue(d.isEmpty())
    d.add(13)
    assertFalse(d.isEmpty())
  }

  @Test def checkPMax():Unit = {
    assertTrue(d.isEmpty())
    d.add(13)
    d.add(11)
    d.add(12) //13 12 11
    assertTrue(d.peekMax() == 13)
  }

  @Test def checkPMin():Unit = {
    assertTrue(d.isEmpty())
    d.add(11)
    d.add(13)
    d.add(12) //13 12 11
    assertTrue(d.peekMin() == 11)
  }

  @Test def checkMin():Unit = {
    assertTrue(d.isEmpty())
    d.add(11)
    d.add(13)
    d.add(12) //13 12 11
    assertTrue(d.min() == 11)
    assertTrue(d.min() == 12)
    assertTrue(d.min() == 13)
  }

  @Test def checkMax():Unit = {
    assertTrue(d.isEmpty())
    d.add(11)
    d.add(13)
    d.add(12) //13 12 11
    assertTrue(d.max() == 13)
    assertTrue(d.max() == 12)
    assertTrue(d.max() == 11)
  }
}