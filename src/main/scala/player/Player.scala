package player

import card.Card

import scala.util.Random

case class Player(name: String, reaction: Double, hand: Seq[Card], stack: Seq[Card]) {

    def placeInHand(cards: Card*): Player = {
        this.copy(hand = hand ++ cards)
    }

    def playCard: Player = {
        // Just in case the hand is empty (this shouldn't happen with this rule set)
        if (hand.isEmpty) {
            return this
        }

        this.copy(hand = hand.drop(1), stack = hand.head +: stack)
    }

    def react: Double = {
        Random.between(1d, 2d) + reaction
    }

    def dropStackToppedWith(cards: (Card, Card)): Player = {
        if (stackNotToppedWith(cards)) {
            return this
        }

        this.copy(stack = Seq.empty)
    }

    def collectStackToppedWith(cards: (Card, Card)): Seq[Card] = {
        if (stackNotToppedWith(cards)) {
            return Seq.empty
        }

        stack
    }

    private def stackNotToppedWith(cards: (Card, Card)): Boolean = {
        stack.isEmpty || (stack.head != cards._1 && stack.head != cards._2)
    }
}

// Following a similar pattern as the Deck object, I want some utility to create players
object Player {

    // This allows us to create players identified as 'CPU' with a name so we can see which player wins
    def cpu(id: Int): Player = {
        Player(s"CPU-$id", Random.between(0.4d, 1d), Seq.empty, Seq.empty)
    }
}
