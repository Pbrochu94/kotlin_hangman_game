package classes

import kotlin.math.roundToInt
import kotlin.text.iterator

class Player(val name: String) {
    var turnToPlay: Boolean = false
    private fun isInvalidFormat(str: String?): Boolean {
        var isInvalid = false
        if (str.isNullOrEmpty()) return true
        val regex = Regex("[\\d\\p{Punct}]")
        return regex.containsMatchIn(input = str)//check if a number or a punct is found in word
    }

    fun makeAGuess(): String {
        var guess: String? = null
        do {
            guess = readln().lowercase().trim()
        } while (isInvalidFormat(str = guess))
        return guess
    }
}

class Session(
    protected var wordToFind: String = "", protected val wordBank: MutableList<String> = mutableListOf(
        "car",
        "tennis",
        "baseball",
        "hoven",
        "golf",
        "gym",
        "elephant",
        "tree",
        "pool",
        "book",
        "water",
        "food",
        "pencil",
        "dragon",
        "cat",
        "chair",
        "summer",
        "halloween",
        "fortnite",
        "radio",
        "orange",
        "hat",
        "headphone",
        "website"
    )
) {
    protected var hangmanStateIndex: Int = 0
    public var hangmanStates: MutableList<String> = mutableListOf(
        "        ======\n        |    ||\n             ||\n             ||\n             ||\n             ||",
        "        ======\n        |    ||\n        0    ||\n             ||\n             ||\n             ||",
        "        ======\n        |    ||\n        0    ||\n        |    ||\n             ||\n             ||",
        "        ======\n        |    ||\n        0    ||\n       /|    ||\n             ||\n             ||",
        "        ======\n        |    ||\n        0    ||\n       /|\\   ||\n             ||\n             ||",
        "        ======\n        |    ||\n        0    ||\n       /|\\   ||\n       /     ||\n             ||",
        "        ======\n        |    ||\n        0    ||\n       /|\\   ||\n       / \\   ||\n       Dead  ||"

    )
    var wordProgress: MutableList<Char> = mutableListOf()
    var letterCalled: MutableList<Char> = mutableListOf()
    var wordCalled: MutableList<String> = mutableListOf()//<---------------------WORK HERE
    protected var gameIsWon: Boolean = false
    protected var gameover: Boolean = false
    protected val players: MutableList<Player> = mutableListOf()
    protected val playersOrder: MutableList<Player> = mutableListOf()
    protected fun printWordProgress() {
        val wordProgressInStr: String = wordProgress.joinToString("")
        println("Word to guess -> $wordProgressInStr")
    }

    protected fun selectFirstPlayer() {
        var rngNmb: Int = Math.random().roundToInt()
        when (rngNmb) {
            0 -> {
                players[0].turnToPlay = true
                println("${players[0].name} is the first to play.")
                //Add to order list
                playersOrder.add(players[0])
                playersOrder.add(players[1])
            }

            1 -> {
                players[1].turnToPlay = true
                println("${players[1].name} is the first to play.")
                //Switch order
                playersOrder.add(players[1])
                playersOrder.add(players[0])
            }
        }
    }

    protected fun printPreviousGuesses() {
        println("Here is all the previous word and characters called:")
        println("Words: $wordCalled")
        println("Letters: $letterCalled")
    }

    protected fun checkIfCharIsFound(wordList: MutableList<Char>, guessedChar: Char): Boolean {
        if (guessedChar in letterCalled) return false
        var letterIsFound: Boolean = false
        for (i in 0..wordToFind.length - 1) {
            if (wordToFind[i] == guessedChar) {
                letterIsFound = true
                wordList[i] = guessedChar
                println(wordList.joinToString(" "))
                println("Letter '$guessedChar' found in the word!")
            }
        }
        if (!letterIsFound) {
            println("$guessedChar is not found in the word.")
        }
        letterCalled.add(guessedChar)
        return letterIsFound
    }

    protected fun checkIfAllLettersFound() {
        val wordProgressInStr: String = wordProgress.joinToString("")
        if (wordProgressInStr == wordToFind) gameIsWon = true
    }

    protected fun initEmptyWordProgress() {
        val wordGrid: MutableList<Char> = mutableListOf()
        for (letter in wordToFind) {
            wordGrid.add('_')
        }
        wordProgress = wordGrid
    }

    protected fun initNewPlayer(): Player {
        var userInput: String?
        do {
            println("Enter a player name:")
            userInput = readlnOrNull()?.trim()
        } while (userInput.isNullOrEmpty())
        return Player(name = userInput)
    }

    protected fun generateWord() {
        wordToFind = wordBank.random()
    }

    public fun startGame() {
        generateWord()
        initEmptyWordProgress()
        for (i in 1..2) {
            players.add(initNewPlayer())
        }
        selectFirstPlayer()
        do {
            playRound()
        } while (!gameIsWon && !gameover)
    }

    public fun endGame() {
        println("You guess the word $wordToFind !")
    }

    public fun playRound() {
        for (player in playersOrder) {
            if (hangmanStateIndex == hangmanStates.size - 1) {
                println(hangmanStates[hangmanStateIndex])
                println("<--------GAME OVER-------->")
                gameover = true
                return
            }//If the hangman is complete, game over
            var playerGuess: String
            var goodGuess: Boolean = false
            do {
                Thread.sleep(2000)
                println(hangmanStates[hangmanStateIndex])
                printWordProgress()
                if (goodGuess) {
                    print("You can play again!\n${player.name}'s turn\n->")
                } else if (!goodGuess) {
                    print("${player.name} can guess a letter or the whole word.\n*type 'log' to see previous guesses*\n->")
                }
                var guessAlreadyCalled: Boolean = false
                playerGuess = player.makeAGuess()
                when {
                    playerGuess == "log" -> printPreviousGuesses()
                    playerGuess.length == 1 -> {
                        if (playerGuess[0] in letterCalled) {
                            println("Letter ${playerGuess[0]} already called")
                            guessAlreadyCalled = true
                        }
                        goodGuess = checkIfCharIsFound(
                            wordList = wordProgress,
                            guessedChar = playerGuess[0]//Using the first and only letter of the guess
                        )
                        checkIfAllLettersFound()
                    }

                    playerGuess.length > 1 -> {
                        if (playerGuess == wordToFind) gameIsWon = true
                        else {
                            wordCalled.add(playerGuess)
                            goodGuess = false
                            println("The word is not $playerGuess")
                        }
                    }
                }
                if (gameIsWon) return endGame()
            } while (guessAlreadyCalled || goodGuess || playerGuess == "log")//
            hangmanStateIndex++
        }
    }

    public fun getPlayers() {
        for (player in players) {
            println(player.name)
        }
    }

}

