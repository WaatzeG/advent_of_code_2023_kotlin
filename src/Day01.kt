fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            line.first { char -> char.isDigit() }.digitToInt() * 10 +
                    line.last { char -> char.isDigit() }.digitToInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val stringDigits = digits.flatMap { (value, keyword) ->
                line.allIndexOf(keyword).map { index -> index to value }
            }.filter { (index, _) -> index >= 0 }

            val digits = listOf(
                line.indexOfFirst { it.isDigit() },
                line.indexOfLast { it.isDigit() }
            ).filter { index -> index >= 0 }
                .map { index -> index to line[index].digitToInt() }

            val allDigitsByIndex = (stringDigits + digits)
                .sortedBy { (index, _) -> index }
                .map { (_, digit) -> digit }

            allDigitsByIndex.first() * 10 + allDigitsByIndex.last()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun String.allIndexOf(keyword: String, startIndex: Int = 0): Sequence<Int> {
    val index = this@allIndexOf.indexOf(keyword, startIndex)
    return if (index < 0) {
        sequenceOf(index)
    } else {
        sequenceOf(index) + allIndexOf(keyword, index + 1)
    }
}

private val digits =
    mapOf(1 to "one", 2 to "two", 3 to "three", 4 to "four", 5 to "five", 6 to "six", 7 to "seven", 8 to "eight", 9 to "nine")

