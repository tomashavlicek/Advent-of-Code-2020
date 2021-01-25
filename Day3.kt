
fun main() {
    val lines = generateSequence {
        readLine()
    }
    val map = lines.toList()

    val slopes = listOf(Slope(1, 1), Slope(3, 1), Slope(5, 1), Slope(7, 1), Slope(1, 2))
    val trees = IntArray(slopes.size)

    slopes.forEachIndexed { index, slope ->
        var t = 0
        var x = 0
        var y = 0
        while (y < map.count() - 1) {
            x += slope.x
            if (x >= map[y].length) {
                x -= map[y].length
            }
            y += slope.y
            if (map[y][x] == '#') {
                t += 1
            }
        }
        trees[index] = t
    }
    println("Trees in all slopes ${trees.fold(1) {acc, i -> acc * i }}")
}

data class Slope(val x: Int, val y: Int)
