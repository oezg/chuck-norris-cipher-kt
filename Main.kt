package chucknorris

tailrec fun main() {
    println("Please input operation (encode/decode/exit):")
    when (val operationName = readln()) {
        "exit" -> {
            println("Bye!")
            return
        }
        "encode" -> {
            println("Input string:")
            encode(readln())
                .onSuccess { encodedString ->
                    println("Encoded string:")
                    println(encodedString)
                }
                .onFailure { e ->
                    println("Failed to encode: ${e.message}.")
                }
        }
        "decode" -> {
            println("Input encoded string:")
            decode(readln())
                .onSuccess {decodedString ->
                    println("Decoded string:")
                    println(decodedString)
                }
                .onFailure { e ->
                    println("Encoded string is not valid: ${e.message}.")
                }
        }
        else -> println("There is no '$operationName' operation")
    }
    println()
    main()
}

fun encode(input: String): Result<String> =
    encodeToBinary(input).map(::binaryToChuck)

fun decode(input: String): Result<String> =
    if (input.contains(Regex("[^0 ]")))
        Result.failure(IllegalArgumentException("The encoded message includes characters other than 0 or spaces"))
    else
        chuckNorrisToBinary(input).bind { decodeBinary(it) }

private fun chuckNorrisToBinary(encodedInput: String): Result<String> =
    runCatching {
        encodedInput
            .split(" ")
            .chunked(size = 2)
            .joinToString("") {
                when {
                    it.size < 2 -> throw IllegalArgumentException("The number of blocks is odd")
                    it.first() == "0" -> "1"
                    it.first() == "00" -> "0"
                    else -> throw IllegalArgumentException("The first block of each sequence is not 0 or 00")
                }.repeat(it.last().length)
            }
    }

private fun decodeBinary(binaryString: String): Result<String> =
    runCatching {
        binaryString
            .chunked(7)
            .map {
                if (it.length < 7)
                    throw IllegalArgumentException("The length of the decoded binary string is not a multiple of 7")
                Integer.parseInt(it, 2) }
            .map { it.toChar() }
            .joinToString("")
    }

private fun binaryToChuck(norris: String): String {
    val result = mutableListOf<String>()
    var counter = 0
    var previous = '#'
    for (digit in norris) {
        if (digit == previous)
            counter++
        else {
            previous = digit
            if (counter > 0)
                result.add("0".repeat(counter))
            counter = 1
            result.add(if (digit == '1') "0" else "00")
        }
    }
    result.add("0".repeat(counter))
    return result.joinToString(" ")
}

private fun encodeToBinary(input: String): Result<String> =
    try {
        Result.success(input.toCharArray().map {
            if (it.code !in 0..127)
                throw IllegalArgumentException("Input string contains non-ASCII characters")
            it.code.toString(2).padStart(7, '0')
        }.joinToString(""))
    } catch (e: IllegalArgumentException) {
        Result.failure(e)
    }

private inline fun <T, R> Result<T>.bind(transform: (T) -> Result<R>): Result<R> =
    if (this.isSuccess)
        transform(this.getOrThrow())
    else
    Result.failure(this.exceptionOrNull()!!)
