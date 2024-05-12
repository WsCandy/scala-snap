package player

import card._
import org.scalatest.funsuite.AnyFunSuite

// This testing class serves as an example of how my approach can easily be tested.
// It's not a comprehensive test suite due to time constrains, but it demonstrates the concept.
class PlayerUnitTests extends AnyFunSuite {
    private val player: Player = Player("Test Player", 1d, Seq(Card(Ace, Spades), Card(Ace, Hearts)), Seq.empty)

    test("should clone the player with the card at the top of their hand now at the top of their stack") {
        val played = player.playCard

        assert(played.stack.head == Card(Ace, Spades))
        assert(played.hand.head == Card(Ace, Hearts))
        assert(played.hand.size == 1)
        assert(played.stack.size == 1)
    }

    test("should place the card at the back of the players hand") {
        val action = player.placeInHand(Card(King, Spades))
        assert(action.hand.last == Card(King, Spades))
    }

    test("should place all the cards at the back of the players hand") {
        val action = player.placeInHand(Card(King, Spades), Card(King, Hearts))
        assert(action.hand == player.hand ++ Seq(Card(King, Spades), Card(King, Hearts)))
    }

    test("should do nothing if the player has no cards to play") {
        val empty = player.copy(hand = Seq.empty)
        val action = empty.playCard

        assert(empty.stack == action.stack)
    }

    test("should drop the stack if it is topped with either of the cards provided") {
        val played = player.copy(hand = Seq.empty, stack = Seq(Card(Ace, Spades), Card(Eight, Hearts)))
        val dropped = played.dropStackToppedWith((Card(Ace, Spades), Card(Eight, Clubs)))

        assert(dropped.stack.isEmpty)
    }

    test("should not drop the stack if it is not topped with either of the cards provided") {
        val played = player.copy(hand = Seq.empty, stack = Seq(Card(Ace, Spades), Card(Eight, Hearts)))
        val action = played.dropStackToppedWith((Card(Eight, Spades), Card(Eight, Diamonds)))

        assert(action.stack.nonEmpty)
    }
}
