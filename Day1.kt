
fun main() {
    val lines = generateSequence {
        readLine()
    }
    val input = lines.toList().map { it.toInt() }

    for (a in input) {
        for (b in input) {
            for (c in input) {
                if (a + b + c == 2020) {
                    println("$a, $b, $c -> ${a * b * c}")
                }
            }
        }
    }
}
