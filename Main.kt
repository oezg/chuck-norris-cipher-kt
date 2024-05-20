package chucknorris

fun main() {
    println("Input string:")
    val input = readln().toCharArray()
    println("\nThe result:")
    val norris = input.map { it.code.toString(2).padStart(7, '0') }.joinToString("")
    println(chuck(norris))
}

fun chuck(norris: String): String {
    val result = mutableListOf<String>()
    var counter = 0
    var previous = '2'
    for (digit in norris) {
        when {
            previous == '2' -> {
                previous = digit
                counter++
                result.add(if (digit == '0') "00" else "0")
            }
            digit == previous -> {
                counter++
            }
            else -> {
                previous = digit
                result.add("0".repeat(counter))
                result.add(if (digit == '0') "00" else "0")
                counter = 1
            }
        }
    }
    result.add("0".repeat(counter))
    return result.joinToString(" ")
}