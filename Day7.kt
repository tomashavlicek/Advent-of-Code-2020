import java.io.File

fun main() {
    val regex_bag = """(\w+ \w+) bags contain (.*)""".toRegex()
    val regex_content = """(\d+) (\w+ \w+) bags?""".toRegex()

    val input = File("src/input_day_7")
        .readText()

    val bags = input.split('\n').map { s: String ->
        val (a, b) = regex_bag.matchEntire(s)!!.destructured
        val list = regex_content.findAll(b).map { matchResult: MatchResult ->
            val (count, bag) = matchResult.destructured
            count.toInt() to Bag(bag, listOf())
        }.toList()
        Bag(a, list)
    }
    println(bags)

    // part 1
    fun recurseUp(color: String) : List<Bag> = bags.filter { bag -> bag.bags.any { it.second.color == color} }
        .let { list -> list + list.flatMap { recurseUp(it.color) } }.distinct()
    println(recurseUp("shiny gold").size)

    // part 2
    fun recurseDown(color: String) : Int = 1 + bags.first { it.color == color }.bags
        .map { (c, bag) -> c * recurseDown(bag.color) }.sum()
    println(recurseDown("shiny gold") - 1)
}

data class Bag(val color: String, val bags: List<Pair<Int, Bag>>)


