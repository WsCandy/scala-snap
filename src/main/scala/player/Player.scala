package player

import card.Card

import scala.util.Random

case class Player(name: String, reaction: Double, hand: Seq[Card], stack: Seq[Card]) {
    
    def placeInHand(cards: Card*): Player = {
        this.copy(hand = hand ++ cards)
    }
}

// Following a similar pattern as the Deck object, I want some utility to create players
object Player {

    // This allows us to create players identified as 'CPU' with a name so we can see which player wins
    def cpu(id: Int): Player = {
        Player(s"CPU-$id", Random.between(0.4, 1), Seq.empty, Seq.empty)
    }
}
