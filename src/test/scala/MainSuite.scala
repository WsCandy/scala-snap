import Sample.sampleMethod
import org.scalatest.funsuite.AnyFunSuite

//these tests are provided purely for convenience, and to illustrate a basic ScalaTest suite setup.
//feel free to delete, modify or extend these to suit your needs. (or switch to a different testing library if you prefer!)
class MainSuite extends AnyFunSuite {

  test("sampleMethod should increment value if above 1") {
    assert(sampleMethod(4) == 5)
  }

  test("sampleMethod should throw an exception for a value below 0") {
    assertThrows[RuntimeException] {
      sampleMethod(-1)
    }
  }
}

object Sample {
  def sampleMethod(sampleInput: Int): Int = {
    if (sampleInput > 0) sampleInput + 1 else throw new RuntimeException("sample exception")
  }
}
