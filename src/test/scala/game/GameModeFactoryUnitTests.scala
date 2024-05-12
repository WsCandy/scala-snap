package game

import org.scalatest.Inspectors.forAll
import org.scalatest.funsuite.AnyFunSuite

class GameModeFactoryUnitTests extends AnyFunSuite {
    val table: Seq[(Int, GameMode)] = Seq(
        (1, ValueMode),
        (2, SuitMode),
        (3, BothMode),
        (4, ValueMode)
    )

    forAll(table) { input =>
        test(s"should return a ${input._2} instance if the value ${input._1} is provided") {
            val mode = GameModeFactory.construct(input._1)
            assert(mode == input._2)
        }
    }
}
