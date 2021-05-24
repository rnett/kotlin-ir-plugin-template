package test

import kotlin.test.Test
import kotlin.test.assertEquals

class Tests{
  @Test
  fun testGetInt(){
    println("Testing on $platformName")
    assertEquals(1, getOne())
  }
}
