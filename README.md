# Backend Technical Task

## Overview
The goal of this task is not to produce the best implementation of the game, but for us to see your coding style
and structure: how you form your code to solve the task and how easy it is to follow and reason from it.

Please focus your time on making sure that the code is well structured and logically split, and that the flow is
easy to understand and follow. In short, write in a form that you yourself would like to inherit a
codebase.

## The Assignment

We would like you to write a command line app in Scala or Java. You are free to use just standard libraries, or
any combination of others that will make the task easier for you, or cleaner. The problem is deliberately not
too complex to focus on the things mentioned above.

The task is to simulate a game of snap between two or more computer players using standard playing card decks.
These requirements should drive your solution in a way that will show us multiple facets of your coding style:

* The application should ask the user how many playing card decks to play
* The application should ask the user whether cards should be matched: on suit, value, or both.
* The application should shuffle the decks before play commences
* The application should simulate a game of snap according to the rules below
* The application should output the winner
* You may choose single or multithreaded approach whichever suits you better, but do include some form of randomness

### Rules of Snap
* First, cards are split equally between players, discarding any excess cards
* Then, each player takes turns placing a card from their hand onto a stack in front of them
* When a card is placed and it matches any other stack, any player may call 'Snap!' (not necessarily the player with the matching stack)
* When a player calls 'Snap!' they take all the cards in the matching stacks
* A player is eliminated when they run out of cards
* The game ends when there is only one player left

### Optional Extensions
* Support more than 2 players
* Instead of always calling 'Snap!' when there are matching cards, have players "miss" a snap
* Support an alternative stop condition where the game ends after all players have placed n cards (where n is taken from user input)

## Guidelines
* We recommend you spend no more than 2 hours on the task.
* Feel free to get it back to us however you like (GitHub, email, ...)

Don't hesitate to get in touch if anything is unclear, or if you have any questions.

## Your thoughts

We find it useful if you can document some of your thinking, including what you would improve given more time. known issues and how you would resolve them given more time. Technical decisions, design decisions, compromises.

Please also add some comments to this file with instructions on how to run your project, and to document what platform and SDK versions you have tested it with.

## This Template project

This project is provided as a skeleton with some basic setup, to save you time, but you are free to add, modify or replace anything, as you prefer.

It currently runs on Scala 2.13.8, and uses [ScalaTest](https://www.scalatest.org/), which supports multiple testing styles - feel free to use any style you prefer.

### Running the project

Run `sbt run` to run the Main class.

You can also run `sbt ~run` to rerun on every file save.

Running `sbt test` will run all tests.
