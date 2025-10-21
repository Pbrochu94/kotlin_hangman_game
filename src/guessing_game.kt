fun guessingGame(){
    val rngNmb:Int = (Math.random()*100).toInt()
    var userInput:Int?
    println(rngNmb)
    do{
        println("Guess the number I think about from 0 to 99 \n->")
        userInput= readln().toIntOrNull()
        when{
            userInput == null -> println("You must enter a number!")
            userInput !in 0..99 -> println("It must be a number between 0 and 99")
            userInput > rngNmb -> println("Its smaller than $userInput")
            userInput < rngNmb -> println("Its bigger than $userInput")
        }
    }while (userInput == null || userInput != rngNmb)
    println("Thats it !\nThe number is indeed $userInput")
}