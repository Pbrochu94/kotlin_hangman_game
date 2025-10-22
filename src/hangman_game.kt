import kotlin.math.roundToInt

class Player(val name: String) {
    var turnToPlay: Boolean = false
    private fun isInvalidFormat(str: String?): Boolean {
        if (str.isNullOrEmpty()) return true
        val regex = Regex("[\\d\\p{Punct}]")
        return regex.containsMatchIn(input = str)//check if a number or a punct is found in word
    }

    fun makeAGuess(): String {
        var guess: String? = null
        do {
            println("Its ${name}'s turn, guess a letter or the whole word:")
            guess = readln().lowercase().trim()
        } while (guess.isNullOrEmpty() || isInvalidFormat(str = guess))
        return guess
    }
}

class Session(protected var word: String = "") {
    protected var hangman: String = "____\n    |\n    |\n    |"
    protected var wordIsFound: Boolean = false
    protected val players: MutableList<Player> = mutableListOf()
    protected val playersOrder: MutableList<Player> = mutableListOf()
    protected fun generateWordToFind() {
        word = "hello"
    }

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

    protected fun addLetterToProgress(wordList: MutableList<Char>, guessedChar: Char) {
        var letterIsFound: Boolean = false
        for (i in 0..word.length - 1) {
            if (word[i] == guessedChar) {
                letterIsFound = true
                wordList[i] = guessedChar
                println(wordList.joinToString(" "))
                println("$guessedChar is found in the word $word at the ${i + 1} place")

            }
        }
        if (!letterIsFound) return println("$guessedChar is not found in the word ${word}")
    }

    protected fun initEmptyWord(): MutableList<Char> {
        val wordGrid: MutableList<Char> = mutableListOf()
        for (letter in word) {
            wordGrid.add('_')
        }
        return wordGrid
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
        for (i in 1..2) {
            players.add(initNewPlayer())
        }
        selectFirstPlayer()
        generateWord()
        do {
            playRound()
        } while (!wordIsFound)
    }

    public fun playRound() {
        var wordProgress: MutableList<Char> = initEmptyWord()
        var guesses: MutableList<String> = mutableListOf()
        for (player in playersOrder) {
            var playerGuess: String = player.makeAGuess()
            if (playerGuess.length == 1) {
                println("You guessed a letter")
                addLetterToProgress(
                    wordList = wordProgress,
                    guessedChar = playerGuess[0]//Using the first and only letter of the guess
                )
            } else {
                println("You guessed a word")
            }
        }
    }

    public fun getPlayers() {
        for (player in players) {
            println(player.name)
        }
    }

}

