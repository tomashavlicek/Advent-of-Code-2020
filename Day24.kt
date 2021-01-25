import java.io.File

fun main() {
    solvePart1()
    solvePart2()
}

private val input = File("src/input_day_24")
    .readLines()

private fun String.walkPath(): Point3D =
    splitPattern
        .findAll(this)
        .map { it.value }
        .fold(Point3D.ORIGIN) { last: Point3D, direction: String ->
            last.hexNeighbor(direction)
        }

private val splitPattern = "([ns]?[ew])".toRegex()

private fun decorateFloor(): Set<Point3D> =
    input
        .map { it.walkPath() }
        .groupBy { it }
        .filter { it.value.size % 2 == 1 }
        .keys

private fun solvePart1() = println(decorateFloor().size)

private fun Set<Point3D>.nextFloor(): Set<Point3D> {
    val pointsToEvaluate = this + (this.flatMap { point -> point.hexNeighbors })
    return pointsToEvaluate.filter { tile ->
        val adjacentBlackTiles = tile.hexNeighbors.count { it in this }
        val black = tile in this
        when {
            black && (adjacentBlackTiles == 0 || adjacentBlackTiles > 2) -> false
            !black && adjacentBlackTiles == 2 -> true
            else -> black
        }
    }.toSet()
}

private fun solvePart2() {
    println(
        generateSequence(decorateFloor()) { it.nextFloor() }
            .drop(100)
            .first()
            .size
    )
}
