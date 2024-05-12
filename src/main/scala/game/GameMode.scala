package game

import card.Card

trait GameMode {
    def selectMatches(cards: Card*): Option[(Card, Card)]
}
