import java.io.File

fun main() {
    val input = File("src/input_day_10")
        .readLines()
        .map { line: String -> line.toInt() }
        .sorted()
        .toMutableList()

    val outlet = 0
    val device = input.last() + 3
    input.add(0, outlet)
    input.add(device)

    val diffs = input.zipWithNext { a, b -> b - a }

    println(device)
    println(input)
    println(diffs)

    println(diffs.count { it == 1 } * diffs.count { it == 3 })

    val f = LongArray(input.size) { 0L }
    f[input.size - 1] = 1
    for (i in input.size - 2 downTo 0) {
        f[i] = f[i + 1]
        if (i + 3 < input.size && input[i + 3] <= input[i] + 3) {
            f[i] += f[i + 3]
        }
        if (i + 2 < input.size && input[i + 2] <= input[i] + 3) {
            f[i] += f[i + 2]
        }
    }
    println(f[0])
}