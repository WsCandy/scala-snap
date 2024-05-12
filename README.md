# Snap!
[![Test](https://github.com/WsCandy/scala-snap/actions/workflows/test.yml/badge.svg)](https://github.com/WsCandy/scala-snap/actions/workflows/test.yml)

Snap is a fun card game you can play with your friends! This project however lets the computer play against itself!

This game has been implemented in Scala 2.13.8 and has been written in a purely functional way. Overall time spent on this project was roughly ~2-3 hours. Please see the `Main.scala` file for details on the planning process.

## Rules and Features
This game takes in three user arguments when the program is run. You should answer the prompts with an `Int` in order for the program to function correctly. There is no validation on user input due to time constrains. So please bear this in mind and enter a positive `Int`.

The game features the following:

1. A variable number of players, specify as many as you like.
2. A variable number of decks, as many as you like.
3. 3 different game modes, match on `Value`, `Suit`, or `Both`!
4. Randomisation - Each player has a reaction time that may help or hinder them in the game.
5. Output as the game plays out. Although the game will play out almost instantly you can view logs of what transpired.

> [!TIP]
> The last player to run out of cards will win! So if you select `Both` and one `Deck` then a player will win be default.

## Running and Testing
To run the project simply run the follow command in a shell of your choosing. This project was developed on MacOS using `sbt` so you will need to have it installed. `sbt` was backed by the following Java Version `Corretto 17.0.11`.

```shell
$ sbt run
```

To run the test suite simply run the following command:

```shell
$ sbt test
```

## Future Improvements
As this task was constrained by time then I wanted to focus on the core functionality and structure of the project rather than a variety of features. The structure in place allows us to add features easily in the future. Here are some ideas:

- Limit the number of rounds played, and call a draw, or declare a winner based on number of cards claimed by calling 'Snap!'.
- Add custom `Deck` types to have more varied game play.
- Add a scoring system and track player scores during gameplay.
- More comprehensive testing suite. The project is entirely functional and simple enough to test.
- Thorough user input validation. Selected settings can be stored in a `Settings` object and validated before the game starts.
- A better experience on the CLI. Allow the user to select options from a list rather than free entry, provide default values etc.

Please see the source files for commentary and explanation of the code.
