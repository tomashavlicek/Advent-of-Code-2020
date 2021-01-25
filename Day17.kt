import java.io.File
import java.lang.IllegalArgumentException

fun main() {
    println(solve1())

    println(solve2())
}

fun solve1() =
    solve { x, y ->
        Point3D(x, y, 0)
    }

fun solve2() =
    solve { x, y ->
        Point4D(x, y, 0, 0)
    }

val source = File("src/input_day_17")
    .readLines()

interface Point {
    val neighbors: List<Point>
}

data class Point3D(val x: Int, val y: Int, val z: Int): Point {

    override val neighbors: List<Point> by lazy {
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).mapNotNull { dz ->
                    Point3D(dx, dy, dz).takeUnless { it == this }
                }
            }
        }
    }

    operator fun plus(other: Point3D): Point3D =
        Point3D(x + other.x, y + other.y, z + other.z)

    fun hexNeighbor(direction: String): Point3D {
        if (direction in HEX_OFFSETS) {
            return HEX_OFFSETS.getValue(direction) + this
        } else {
            throw IllegalArgumentException("No such $direction defined in $HEX_OFFSETS")
        }
    }

    val hexNeighbors: List<Point3D> by lazy {
        return@lazy HEX_OFFSETS.map { this + it.value }
    }

    companion object {
        val ORIGIN = Point3D(x = 0, y = 0, z = 0)
        private val HEX_OFFSETS = mapOf(
            "e" to Point3D(1, -1, 0),
            "w" to Point3D(-1, 1, 0),
            "ne" to Point3D(1, 0, -1),
            "nw" to Point3D(0, 1, -1),
            "se" to Point3D(0, -1, 1),
            "sw" to Point3D(-1, 0, 1)
        )
    }
}

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int): Point {

    override val neighbors: List<Point> by lazy {
        (x - 1..x + 1).flatMap { dx ->
            (y - 1..y + 1).flatMap { dy ->
                (z - 1..z + 1).flatMap { dz ->
                    (w - 1..w + 1).mapNotNull { dw ->
                        Point4D(dx, dy, dz, dw).takeUnless { it == this }
                    }
                }
            }
        }
    }
}

private fun parseInput(input: List<String>, pointFunction: (Int, Int) -> Point): Map<Point, Boolean> {
    val points = mutableMapOf<Point, Boolean>()
    for (x in input.indices) {
        for (y in input[x].indices) {
            points[pointFunction(x, y)] = input[x][y] == '#'
        }
    }
    return points
}

private fun Map<Point, Boolean>.nextCycle(): Map<Point, Boolean> {
    val nextMap = this.toMutableMap()
    keys.forEach { point ->
        point.neighbors.forEach { neighbor ->
            nextMap.putIfAbsent(neighbor, false)
        }
    }
    nextMap.entries.forEach { (point, active) ->
        val activeNeighbors = point.neighbors.count { this.getOrDefault(it, false) }
        nextMap[point] = when {
            active && activeNeighbors in setOf(2, 3) -> true
            !active && activeNeighbors == 3 -> true
            else -> false
        }
    }
    return nextMap
}

private fun solve(rounds: Int = 6, pointFunction: (Int, Int) -> Point): Int {
    var conwayGrid = parseInput(source, pointFunction)
    repeat(rounds) {
        conwayGrid = conwayGrid.nextCycle()
    }
    return conwayGrid.count { it.value }
}



