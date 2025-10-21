

class Stack<E:Animal>{
    var content: MutableList<E?> = mutableListOf()
    fun printContent(){
        for(animal in content){
            println(animal?.name)
        }
    }
    fun push(itemToAdd: E) {
        content.add(itemToAdd)
    }
    fun pop():E?{
        if(!content.isEmpty()){
            return content.removeAt(content.lastIndex)
        }
        println("No value in stack")
        return null
    }
}

open class Animal(open var name:String = "Shadow"){
    private var owner:String = "Pat"
    fun greeting(){
        println("Woof")
    }
    var age:Int = 0
        set(value) {
            if(value > 0){0
                field = value
            }
        }
    fun changeOwner(newOwner : String){
        println("Owner: $owner")
        owner = newOwner
        println("Owner: $owner")
    }
}

class Canine():Animal(){
    fun printName(){
        println(this.name)
    }
}