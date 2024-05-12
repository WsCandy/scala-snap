package deck

import card._

import scala.util.Random

case class Deck(cards: Seq[Card]) {

    def shuffled: Deck = {
        this.copy(cards = Random.shuffle(cards))
    }
}

object Deck {

    // I like the idea of having different types of decks, so I'm introducing some static constructors.
    def standard: Deck = {
        val values: Set[CardValue] = Set(Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)
        val suits: Set[CardSuit] = Set(Spades, Hearts, Diamonds, Clubs)

        Deck(create(values, suits))
    }

    // This method can be used to support the creation of custom decks in the future.
    private def create(values: Set[CardValue], suits: Set[CardSuit]): Seq[Card] = {
        val set = suits flatMap { suit =>
            for (value <- values)
                yield Card(value, suit)
        }

        set.toSeq
    }
}
