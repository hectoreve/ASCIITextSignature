package signature

import java.io.File

fun main() {
    val romanFile = File("F:roman.txt")
    val mediumFile = File("F:medium.txt")
    print("Enter name and surname: ")
    val nameInput = readLine()!!
    print("Enter person's status: ")
    val statusInput = readLine()!!
    val name = AsciiWord(romanFile, nameInput, 10)
    val status = AsciiWord(mediumFile, statusInput, 5)

    val size = Integer.max(name.length, status.length)

    printHeader(size)
    printFormattedWord(name, size)
    printFormattedWord(status, size)
    printFooter(size)

}

fun printHeader(size: Int){
    print("8888")
    repeat(size) { print("8") }
    print("8888\n")
}

fun printFooter(size: Int){
    print("8888")
    repeat(size) { print("8") }
    print("8888")
}

fun printFormattedWord(asciiWord: AsciiWord, size: Int){
    for (i in 0 until asciiWord.height){
        print("88  ")
        repeat((size - asciiWord.length)  / 2 ) { print(" ") }
        print(asciiWord.toPrintWord()[i])
        repeat((size + 1 - asciiWord.length) / 2 ) { print(" ") }
        print("  88\n")
    }
}

class AsciiWord(private val file: File, private val word: String, private val spaceValue:Int){
    private val letterList = makeAsciiList()
    val height = file.readLines()[0].split(" ")[0].toInt()
    val length = toPrintWord()[0].length

    private fun makeAsciiList(): List<AsciiLetter> {
        val retorno = mutableListOf<AsciiLetter>()
        for (i in word.indices){
            retorno.add(AsciiLetter(file, word[i].toString()))
        }
        return retorno
    }

    fun toPrintWord(): MutableList<String>{
        val toPrint:MutableList<String> = generate(height, "")
        var space = ""
        repeat(spaceValue){
            space = space.plus(" ")
        }
        for (i in 0 until height){
            for((j, character) in word.withIndex()) {
                if(character != ' ')
                    toPrint[i] = toPrint[i].plus(letterList[j].wordArray[i])
                else{
                    toPrint[i] = toPrint[i].plus(space)
                }
            }
        }
        return toPrint
    }

    private fun <T> generate(size: Int, value: T): MutableList<T> {
        return (0 until size).map { value }.toMutableList()
    }

}


class AsciiLetter(private val file: File, private val letter: String){
    private val height = defineHeight()
    private val width = defineWidth()
    val wordArray = generateWordArray()

    private fun generateWordArray(): List<String> {
        val retorno = mutableListOf<String>()
        for (i in 1 until file.readLines().size step height + 1){
            if(file.readLines()[i].split(" ")[0] == letter){
                for (j in 1 .. height)
                    retorno.add(file.readLines()[i + j])
            }
        }
        return retorno
    }


    private fun defineWidth(): Int {
        for (i in 1 until file.readLines().size step height + 1){
            if(file.readLines()[i].split(" ")[0] == letter){
                return file.readLines()[i].split(" ")[1].toInt()
            }
        }
        return 0
    }

    private fun defineHeight() = file.readLines()[0].split(" ")[0].toInt()

}
