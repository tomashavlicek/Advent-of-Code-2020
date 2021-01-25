import java.io.File

fun main() {
    println(solvePart1())

    println(solvePart2())
}

private fun solvePart1(): Int = countRuleMatches()

private fun solvePart2(): Int {
    rules[8] = listOf(
        listOf(RuleReference(42)),
        listOf(RuleReference(42), RuleReference(8))
    )
    rules[11] = listOf(
        listOf(RuleReference(42), RuleReference(31)),
        listOf(RuleReference(42), RuleReference(11), RuleReference(31))
    )
    return countRuleMatches()
}

private val input = File("src/input_day_19").readLines()
private val rules: MutableMap<Int, List<List<Day19>>> = parseRules(input)
private val messages: List<String> = input.dropWhile { it.isNotBlank() }.drop(1)

interface Day19
class Atom(val symbol: Char): Day19
class RuleReference(val id: Int): Day19

private fun parseRules(input: List<String>): MutableMap<Int, List<List<Day19>>> =
    input.takeWhile { it.isNotBlank() }.map { line ->
        val (id, rhs) = line.split(": ")
        val sides = rhs.split(" | ")
        id.toInt() to sides.map { side ->
            side.split(' ').map { part ->
                if (part.startsWith('"')) Atom(symbol = part[1])
                else RuleReference(id = part.toInt())
            }
        }
    }.toMap().toMutableMap()

private fun String.ruleMatch(ruleId: Int, position: Int = 0): List<Int> =
    rules.getValue(ruleId).flatMap { listOfRules -> // OR Rule
        var positions = listOf(position)
        listOfRules.forEach { rule ->  // AND Rule
            positions = positions.mapNotNull { idx ->
                when {
                    rule is Atom && getOrNull(idx) == rule.symbol ->
                        listOf(idx + 1) // End condition
                    rule is RuleReference ->
                        ruleMatch(rule.id, idx)
                    else ->
                        null
                }
            }.flatten()
        }
        positions
    }

private fun countRuleMatches(): Int =
    messages.count { message ->
        message.ruleMatch(0).any { it == message.length }
    }