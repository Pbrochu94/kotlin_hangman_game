import kotlin.math.roundToInt

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
            println("Its ${name}'s turn, guess a letter or the whole word:")
            guess = readln().lowercase().trim()
        } while (isInvalidFormat(str = guess))
        return guess
    }
}

class Session(protected var word: String = "") {
    protected var hangman: String = "____\n    |\n    |\n    |"
    var wordProgress: MutableList<Char> = mutableListOf()
    var letterCalled: MutableList<Char> = mutableListOf()
    var wordCalled: MutableList<String> = mutableListOf()
    protected var wordIsFound: Boolean = false
    protected val players: MutableList<Player> = mutableListOf()
    protected val playersOrder: MutableList<Player> = mutableListOf()

    protected fun selectFirstPlayer() {
        var rngNmb: Int = Math.random().roundToInt()
        when (rngNmb) {
            0 -> {
                players[0].turnToPlay = true
                println("${players[0].name} is starting")
                //Add to order list
                playersOrder.add(players[0])
                playersOrder.add(players[1])
            }

            1 -> {
                players[1].turnToPlay = true
                println("${players[1].name} is starting")
                //Switch order
                playersOrder.add(players[1])
                playersOrder.add(players[0])
            }
        }
    }

    protected fun checkIfCharIsFound(wordList: MutableList<Char>, guessedChar: Char): Boolean {
        if (guessedChar in letterCalled) return false
        var letterIsFound: Boolean = false
        for (i in 0..word.length - 1) {
            if (word[i] == guessedChar) {
                letterIsFound = true
                wordList[i] = guessedChar
                println(wordList.joinToString(" "))
                println("$guessedChar is found in the word $word at the ${i + 1} place")
            }
        }
        if (!letterIsFound) {
            println("$guessedChar is not found in the word ${word}")
        }
        letterCalled.add(guessedChar)
        return letterIsFound
    }

    protected fun initEmptyWordProgress() {
        val wordGrid: MutableList<Char> = mutableListOf()
        for (letter in word) {
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
        word = "hello"
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
        } while (!wordIsFound)
    }

    public fun playRound() {
        for (player in playersOrder) {
            var playerGuess: String
            var goodGuess: Boolean = false
            do {
                var guessAlreadyCalled: Boolean = false
                playerGuess = player.makeAGuess()
                if (playerGuess.length == 1) {
                    println("You guessed a letter")
                    if (playerGuess[0] in letterCalled) {
                        println("Letter ${playerGuess[0]} already called")
                        guessAlreadyCalled = true
                    }
                    goodGuess = checkIfCharIsFound(
                        wordList = wordProgress,
                        guessedChar = playerGuess[0]//Using the first and only letter of the guess
                    )
                } else {
                    println("You guessed a word")
                }
            } while (guessAlreadyCalled || goodGuess)
        }
    }

    public fun getPlayers() {
        for (player in players) {
            println(player.name)
        }
    }

}

