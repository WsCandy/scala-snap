package game

import card.Card

case object ValueMode extends GameMode {

    // The number of cards is variable depending on player count, so we need to bear this in mind
    def selectMatches(cards: Card*): Option[(Card, Card)] = {
        // If there is only one card then there can't be a match
        if (cards.length < 2) {
            return None
        }

        // The easiest way to check is to sort them by their matching criteria and then see if the first two match
        val sorted = cards sortWith { (a, b) => a.value.toString > b.value.toString }
        val first = sorted.head
        val second = sorted(1)

        if (first.value != second.value) {
            return None
        }

        Some(first, second)
    }
}
