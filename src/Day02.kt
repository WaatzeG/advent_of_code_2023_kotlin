data class Game(val ID: Int, val draws: List<Draw>)
data class Draw(val color: Color, val amount: Int)
enum class Color { red, green, blue }

fun inputToGames(lines: List<String>): List<Game> {
    return lines.map { parseLine(it) }
}

fun parseLine(line: String): Game {
    return Regex("^Game\\s(?<game>\\d+):\\s(?<gameContent>((?<number>\\d+)\\s(?<color>\\w+)[,|;]?\\s?)+)")
        .find(line)
        .let {
            val gameID = it!!.groups["game"]!!.value.toInt()
            val game = it.groups["gameContent"]!!.value
            val draws = parseDraws(game)
            Game(gameID, draws)
        }
}

fun parseDraws(gameContent: String): List<Draw> {
    val gameContents = Regex("(?<amount>\\d+)\\s(?<color>\\w+)")
    return gameContents.findAll(gameContent).map {
        val numberOfCubes = it.groups["amount"]!!.value.toInt()
        val color = it.groups["color"]!!.value
        Draw(Color.valueOf(color), numberOfCubes)
    }.toList()
}

fun main() {
    fun part1(input: List<String>): Int {
        val limits = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14,
        )
        return inputToGames(input)
            .filter { game -> game.draws.all { draw -> draw.amount <= limits.getValue(draw.color.name) } }
            .sumOf { game -> game.ID }
    }

    fun part2(input: List<String>): Int {
        return inputToGames(input).sumOf { it ->
            it.draws
                .groupBy { draw -> draw.color }
                .values.map { draws -> draws.maxOf(Draw::amount) }
                .fold(1) { cur, acc -> cur * acc } as Int
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
