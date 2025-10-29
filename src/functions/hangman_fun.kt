package functions

fun welcome(): Boolean {
    println("WELCOME TO MY HANGMAN GAME")
    println("-----------------------------------")
    var userInput: String = ""
    do {
        println("Type 'start' to start a game or 'quit' to exit program\n->")
        userInput = readln().lowercase()
    } while (userInput !in listOf("start", "quit"))
    if (userInput == "quit") return false else return true
}

fun playAgain(): Boolean {
    val validPrompt: MutableList<String> = mutableListOf("yes", "no")
    var userInput: String = ""
    do {
        println("Do you want to play again?('yes' or 'no')")
        userInput = readln().lowercase()
    } while (userInput !in validPrompt)
    if (userInput == "yes") return true else return false
}

