import card.Card
import deck.Deck
import game.{GameMode, GameModeFactory}
import player.Player

import scala.io.StdIn
import scala.util.Random

/**
 * I'm just going to use this section to write my initial thoughts.
 *
 * The game of snap is fairly simple, I think the best approach would be to model the data required to play the game as a first step.
 * - We need to model the `Card`s themselves, each `Card` has a value, `Ace` through to `King`, and a suit, `Spades`, `Hearts`, `Diamonds`, and `Clubs`.
 * - While it would be nice to use `int` for the values I think for clarity it would be better to use a string or enum to represent the value.
 * - The suit can also be represented enum or a string.
 * - I'll probably go for a enum as as these values will not change, are mutually exclusive, and comparison will be simple.
 * - I will need to model a `Deck` too, which is a collection of cards, this collection can be a `Set` as all cards in a `Deck` are unique.
 *
 * I have the need to model a 'Player' a 'Player' has a hand of cards and a stack of cards, we can assign a player a unique name or a number too, so they can be identified.
 * - The hand and stack of cards can be represented as a list of cards, and has the requirement of changing as the game progresses.
 * - The output of the program will be the 'Player' that wins.
 * - In real life the winner of a round of Snap tends to have the fastest reaction times, we could generate a random number to represent a weighting of this and use it to determine the winner after a card has been placed.
 *
 * The game has different 'game modes' that can be played, which is controlled via player input. Free text entry is prone to errors so this is taken into consideration when accepting user input.
 * - I can represent the 'game modes' as an enum as it's just a small list, and they're exclusive to each other.
 * - The game modes are `Value`, `Suit`, and `Both`.
 *
 * These models are flexible enough to allow for future expansion, such as adding more players, or different game modes, or even brand new cards without the need to edit core game logic.
 *
 * The next step would be to map out what functions are required. I believe it's best to model the functions as actions a player would take, or actions that progress the same forward.
 * Following this structure also makes the code easier to read, understand, and test.
 *
 * The main gameplay loop can either be a while loop or a recursive function, my initial thoughts are to use a recursive function, for a functional approach, but let's see how that plays out when I code it.
 *
 * The initial list of functions are detailed below:
 * - `createDeck` - This function will create a Deck (Set<Card>) of 52 cards, in a standard order.
 * - `shuffleDeck` - This function, given an input of Deck, will return a new shuffled Deck.
 * - `dealCards` - This function, with the input of Collection<Deck> and a number of players will output a collection of <Player> with a hand of cards and an empty stack.
 * - `playCard` - This function will take player, and place a card from their hand into their stack, the output will be a player with a new hand and stack.
 * - `isSnap` - This function will take in all the players, and the game mode, and check if any stacks have the same card on top, it will return a boolean, this is then used determine if a the players will call snap.
 * - `snap` - This function is executed if `isSnap` returned true. The input will be all the players, and the output will be the all players with the appropriate cards redistributed. This function will remove eliminated players from the return value.
 * - The above `snap` function seems to do a lot, there is potential for this to be broken down further, we will see during implementation.
 * - `playRound` - This function will take in all the players, the game mode, and the output will be the players after a round has been played. When 1 player is left, they're the winner. This will be the recursive function that will be called.
 *
 * The above breakdown is my initial plan and what I am aiming to implement. There's a chance the final result may be different but I will leave my above thoughts in place so it's visible how much I may or may not deviate.
 */
object Main {

    def main(args: Array[String]): Unit = {
        // I'm going to add some rudimentary input handling here, normally the input would be validated but due to time constraints I'm going to ignore that for now.
        // This would be the number one thing I'd improve given more time.
        println("How many decks do you want to play with? Please enter an integer.")
        val totalDecks = StdIn.readInt()

        println("\nHow many players do you want to play with? Please enter an integer.")
        val totalPlayers = StdIn.readInt()

        println("\nHow should the cards be matched? Please enter an integer.\n1. Value\n2. Suit\n3. Both")
        val mode = GameModeFactory.construct(StdIn.readInt())

        println(s"\nStarting the game with $totalDecks deck(s), $totalPlayers player(s), and a Game Mode of $mode.\n")

        val decks = for (_ <- 0 until totalDecks)
            yield Deck.standard.shuffled

        val players = for (i <- 0 until totalPlayers)
            yield Player.cpu(i)

        val dealt = dealCards(decks, players)
        val winner = playRound(mode, dealt, 0, 0)

        println(s"The winning player is... *drum roll* ${winner.name}!")
    }

    // This is the main gameplay loop, it will run recursively until there is only one player left.
    // Breakdown:
    // 1. If there is only one player, exit and declare them the winner
    // 2. Player plays a card
    // 3. Check if snap is called
    // 4. If snap is called, redistribute cards
    // 5. After distributing the cards, eliminate any players that have no cards left
    // 5. If snap is not called, move to the next round (call the function again)
    private def playRound(mode: GameMode, players: Seq[Player], turn: Int, round: Int): Player = {
        if (players.size == 1) {
            println(s"Game over after $round rounds!")
            return players.head
        }

        // The table is the state of all the players after playing a card
        val table = for (i <- players.indices) yield {
            if (i == turn) players(i).playCard
            else players(i)
        }

        val matches = getMatches(mode, table)

        // End the round
        if (matches.isEmpty) {
            return endRound(mode, table, turn, round)
        }

        val winner = getRoundWinner(table)

        if (winner.isEmpty) {
            println(s"Oops, a snap was missed! - Match was ${matches.get}")
            return endRound(mode, table, turn, round)
        }

        val cards = table flatMap { player => player.collectStackToppedWith(matches.get) }
        println(s"${winner.get.name} says 'Snap!' and wins ${cards.size} cards!- Match was ${matches.get}")

        // The winner collects the cards
        val distributed = for (player <- table) yield {
            if (player == winner.get) player.placeInHand(cards: _*).dropStackToppedWith(matches.get)
            else player.dropStackToppedWith(matches.get)
        }

        endRound(mode, distributed, turn, round)
    }

    private def getRoundWinner(players: Seq[Player]): Option[Player] = {
        // Let's add a small chance for there to be no winner
        if (Random.between(1, 10) == 1) {
            return None
        }

        // Each player rolls a number, to see who reacted the fastest
        val rolls = players map { player => player.react }

        Some(players(rolls.indexOf(rolls.min)))
    }

    private def eliminatePlayers(players: Seq[Player]): Seq[Player] = {
        players filter { player =>
            if (player.hand.isEmpty) {
                println(s"${player.name} has been eliminated!")
            }

            player.hand.nonEmpty
        }
    }

    private def endRound(mode: GameMode, players: Seq[Player], turn: Int, round: Int): Player = {
        val remaining = eliminatePlayers(players)
        val next = nextTurn(remaining, turn)

        playRound(mode, remaining, next, round + 1)
    }

    private def nextTurn(players: Seq[Player], turn: Int): Int = {
        if (turn + 1 >= players.size) 0 else turn + 1
    }

    // My original plan was to return a boolean, but it's useful to return a tuple of the cards that match, so I can process the stacks after.
    private def getMatches(mode: GameMode, table: Seq[Player]): Option[(Card, Card)] = {
        // Get all the cards at the top of the stacks, some players may not have placed a card yet
        val cards = table.map(player => player.stack.headOption)
            .filter(card => card.isDefined)
            .map(card => card.get)

        // Each mode has different matching rules so let's contain that logic in the mode itself
        // The implementation is crude, given more time this could be cleaned up
        mode.selectMatches(cards: _*)
    }

    // Now we have the cards and the players, we need to deal the cards to the players hand.
    private def dealCards(decks: Seq[Deck], players: Seq[Player]): Seq[Player] = {
        val cards = decks flatMap { deck => deck.cards }

        // We need to split these evenly, we know how many players we have so this is simple.
        val discard = cards.length % players.length

        // We then discard cards from the deck and deal those
        val deal = cards.drop(discard)
        val perPlayer = deal.size / players.size

        // Rather than going around the table we just take chunks of the deck, they're shuffled so the end result is fine
        for (i <- players.indices) yield {
            val start = i * perPlayer
            val cards = deal.slice(start, start + perPlayer)
            players(i).placeInHand(cards: _*)
        }
    }
}
