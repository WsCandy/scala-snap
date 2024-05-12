import card._
import deck.Deck
import game.ValueMode
import org.scalatest.funsuite.AnyFunSuite
import player.Player

// This testing class serves as an example of how my approach can easily be tested.
// It's not a comprehensive test suite due to time constrains, but it demonstrates the concept.
class MainUnitTests extends AnyFunSuite {
    val players: Seq[Player] = Seq(Player.cpu(0), Player.cpu(1))
    val deck: Deck = Deck(
        Seq(Card(Ace, Spades), Card(Ace, Hearts), Card(Ace, Clubs))
    )

    test("should deal the cards to the players, discard the first card, and distribute the remaining") {
        val dealt = Main.dealCards(Seq(deck), players)

        dealt foreach { player =>
            assert(player.hand.head != Card(Ace, Spades))
            assert(player.hand.size == 1)
        }

        assert(dealt.head.hand.head == Card(Ace, Hearts))
        assert(dealt.last.hand.head == Card(Ace, Clubs))
    }

    test("should return matches if the cards at the top of the stacks match value") {
        val dealt = Main.dealCards(Seq(deck), players)
        val table = dealt map { player => player.playCard }
        val matches = Main.getMatches(ValueMode, table)

        assert(matches.isDefined)
        assert(matches.contains((Card(Ace, Hearts), Card(Ace, Clubs))))
    }
}
