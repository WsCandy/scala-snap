import deck.Deck

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
        // I'm adding these as static vars for now, but building with it in mind that these will be provided by the user.
        val totalDecks = 1

        val decks = for (_ <- 0 until totalDecks)
            yield Deck.standard

        println(decks)
    }
}
