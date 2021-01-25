fun main() {
    val input = "1000067\n" +
            "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,439,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19"
    val (depart, ids) = input.split("\n")

    val filteredIds = ids.split(',')
        .filterNot { it == "x" }
        .map { it.toInt() }

    var closest = depart.toInt()
    var best = 0
    for (id in filteredIds) {
        if ((id - (depart.toInt() % id)) < closest) {
            closest = id - (depart.toInt() % id)
            best = id
        }
    }

    println(closest * best)

    // Part 2
    val busses = ids
        .split(',')
        .mapIndexedNotNull { index, i -> if (i == "x") null else IndexedBus(index, i.toLong()) }

    var stepSize = busses.first().bus
    var time = 0L
    busses.drop(1).forEach { (offset, bus) ->
        while ((time + offset) % bus != 0L) {
            time += stepSize
        }
        stepSize *= bus // New Ratio!
    }
    println(time)
}

data class IndexedBus(val index: Int, val bus: Long)