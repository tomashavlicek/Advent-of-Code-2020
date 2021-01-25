import java.io.File

fun main() {
    val ruleRegex = """([a-z ]+): (\d+)-(\d+) or (\d+)-(\d+)""".toRegex()

    val (inputRules, inputMyTicket, inputNearbyTickets) = File("src/input_day_16")
        .readText()
        .split("\n\n")

    val rules = inputRules.split("\n").map { s: String ->
        val (name, a1, a2, b1, b2) = ruleRegex.matchEntire(s)!!.destructured
        Rule(name, listOf(a1.toInt()..a2.toInt(), b1.toInt()..b2.toInt()))
    }

    val myTicket = inputMyTicket.split("\n").drop(1).first().let { s: String -> s.split(",").map { it.toInt() } }
    val nearbyTickets = inputNearbyTickets.split("\n").drop(1).map { s: String -> s.split(",").map { it.toInt() } }


    fun match(value: Int) = rules.any { rule -> rule.ranges.any { intRange -> value in intRange } }

    fun part1(): Int {
        return nearbyTickets.flatten().filterNot { match(it) }.sum()
    }

    println(part1())

    fun part2() : Long {
        val options = myTicket.map { rules.map { it.name }.toMutableSet() }

        nearbyTickets.filterNot { t -> t.any { !match(it) } }.forEach { ticket ->
            ticket.forEachIndexed { index, value ->
                val validRules = rules.filter { r -> r.ranges.any { value in it } }.map { it.name }.toSet()
                options[index].removeIf { !validRules.contains(it) }
            }
        }

        while(options.any { it.size > 1 }) {
            val uniques = options.filter { it.size == 1 }.flatten()
            options.filter { it.size > 1 }.forEach { it.removeAll(uniques) }
        }

        return options.mapIndexedNotNull { index, set -> if(set.first().startsWith("departure")) index else null }
            .map { myTicket[it].toLong() }
            .reduce { a, b -> a * b }

    }

    println(part2())
}

data class Rule(val name: String, val ranges: List<IntRange>)

