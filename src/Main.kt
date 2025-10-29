import classes.Session
import functions.playAgain
import functions.welcome

fun main() {
    var gameOn: Boolean = welcome()
    while (gameOn) {
        val gameSession: Session = Session()
        gameSession.startGame()
        gameOn = playAgain()
    }
    println("Exiting program...")
}