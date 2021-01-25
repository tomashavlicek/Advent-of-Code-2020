import java.io.File

fun main() {

    val lines = generateSequence {
        readLine()
    }

    val data: Sequence<Set<Char>> = sequence {
        var m = mutableSetOf<Char>()
        for (line in lines) {
            if (line.isEmpty()) {
                yield(m)
                m = mutableSetOf()
            } else {
                for (c in line) {
                    m.add(c)
                }
            }
        }
        yield(m)
    }

    val part1 = File("src/input_day_6")
        .readText()
        .split("\n\n")
        .sumBy { s: String -> s.filter { c: Char -> c != '\n' }.toSet().size }
    println(part1)

    val part2 = File("src/input_day_6")
        .readText()
        .split("\n\n")
        .map { it.split("\n").filter { it.isNotBlank() }.reduce { l, r -> r.filter { it in l } } }.sumBy { it.count() }
    println(part2)


//    part 1
//    println(data.fold(0) { acc: Int, set: Set<Char> -> acc + set.size })

//    part 2
    generateSequence {
        generateSequence { readLine() }
            .takeWhile { it != "" }
            .toList()
            .ifEmpty { return@generateSequence null }
            .map { it.toCharArray().toSet() }
            .reduce { acc, cur -> acc.intersect(cur) }
            .count()
    }.sum().let(::println)


    println("Day 6")
}