package player

import card.Card

case class Player(name: String, reaction: Double, hand: Seq[Card], stack: Seq[Card])
