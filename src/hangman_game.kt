import kotlin.math.roundToInt

class Player(val name: String) {
    var turnToPlay: Boolean = false
    var guesses: MutableList<String> = mutableListOf()
    private fun isInvalidFormat(str: String?): Boolean {
        if (str.isNullOrEmpty()) return true
        val regex = Regex("[\\d\\p{Punct}]")
        return regex.containsMatchIn(input = str)//check if a number or a punct is found in word
    }

    fun makeAGuess(): String {
        var guess: String? = null
        do {
            println("Its ${name}'s turn, guess a letter or the whole word:")
            guess = readln().lowercase()
        } while (guess.isNullOrEmpty() || isInvalidFormat(str = guess))
        return guess
    }
}

class Session() {
    protected var word: String? = null
    protected var hangman: String = "____\n    |\n    |\n    |"
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

    protected fun initEmptyWord(): MutableList<Char> {
        val wordGrid: MutableList<Char> = mutableListOf()
        for (letter in word!!) {
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
        for (player in playersOrder) {
            var playerGuess: String = player.makeAGuess()
            println(playerGuess.length)
            if (playerGuess.length == 1) println("You guessed a letter")
            else println("You guessed a word")
        }
    }

    public fun getPlayers() {
        for (player in players) {
            println(player.name)
        }
    }

}

