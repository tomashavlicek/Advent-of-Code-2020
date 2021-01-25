import java.io.File

val deltas = listOf(
    Pair(-1, 1), Pair(-1, 0), Pair(-1, -1),
    Pair(0, 1), Pair(0, -1),
    Pair(1, 1), Pair(1, 0), Pair(1, -1))

fun main() {
    val input = File("src/input_day_11")
        .readLines()

    var current = input.map { it.toList() }
    var next = part2(current)
    while (current != next) {
        current = next
        next = part2(current)
    }

    // part 1
    println(next.flatMap { it.toList() }.count { it == '#' })
}

// part 1
fun part1(input: List<List<Char>>): List<List<Char>> {
    val layout = input.map { it.toMutableList() }.toMutableList()

    for (x in input.indices) {
        for (y in input[x].indices) {
            val seat = input[x][y]

            var adjecentSeats = ""
            for (delta in deltas) {
                val dx = delta.first + x
                val dy = delta.second + y
                if (dx in input.indices && dy in input[x].indices) {
                    adjecentSeats += input[dx][dy]
                }
            }

            if (seat == 'L' && adjecentSeats.count { it == '#' } == 0) {
                layout[x][y] = '#'
            } else if (seat == '#' && adjecentSeats.count { it == '#' } >= 4) {
                layout[x][y] = 'L'
            } else {
                layout[x][y] = seat
            }
        }
    }

    for (row in layout) {
        println(row)
    }
    println()

    return layout
}

// part 2
fun part2(input: List<List<Char>>): List<List<Char>> {
    val layout = input.map { it.toMutableList() }.toMutableList()

    for (x in input.indices) {
        for (y in input[x].indices) {
            val seat = input[x][y]

//            val adjacentSeats = deltas.mapNotNull { direction: Pair<Int, Int> ->
//                var dx = x
//                var dy = y
//                var adjacentSeat: Char? = '.'
//                do {
//                    dx += direction.first
//                    dy += direction.second
//                    adjacentSeat = input.getOrNull(dx)?.getOrNull(dy)
//                } while (adjacentSeat == '.')
//                adjacentSeat
//            }

            var adjecentSeats = ""
            for (delta in deltas) {
                var dx = x
                var dy = y
                var adjecentSeat = '.'
                do {
                    dx += delta.first
                    dy += delta.second
                    if (dx in input.indices && dy in input[x].indices) { // tabulka nemusi byt rovnostranna !!!
                        adjecentSeat = input[dx][dy]
                    } else {
                        break
                    }
                } while (adjecentSeat == '.')
                adjecentSeats += adjecentSeat
            }

            if (seat == 'L' && adjecentSeats.count { it == '#' } == 0) {
                layout[x][y] = '#'
            } else if (seat == '#' && adjecentSeats.count { it == '#' } >= 5) {
                layout[x][y] = 'L'
            } else {
                layout[x][y] = seat
            }
        }
    }

    for (row in layout) {
        println(row)
    }
    println()

    return layout
}

fun countOccupiedSeats(seats: List<List<Char>>): Int {
    var seatsBefore: List<List<Char>>
    var seatsAfter =  listOf<List<Char>>()
    do {
        seatsBefore = seatsAfter.ifEmpty { seats }
        seatsAfter = seatsBefore.mapIndexed { y, row ->
            row.mapIndexed { x, seat ->
                val occupiedAdjacentSeats = occupiedAdjacentSeats(seatsBefore, y, x)
                when {
                    seat == '.' -> '.'
                    occupiedAdjacentSeats.isEmpty() -> '#'
                    occupiedAdjacentSeats.size >= 5 -> 'L'
                    else -> seat
                }
            }
        }
    } while (seatsBefore != seatsAfter)
    return seatsAfter.flatten().filter { it == '#' }.size
}

private fun occupiedAdjacentSeats(
    seats: List<List<Char>>,
    y: Int,
    x: Int
): List<Char> {
    val directions = listOf(
        Pair(0, -1),
        Pair(0, +1),
        Pair(-1, 0),
        Pair(-1, +1),
        Pair(-1, -1),
        Pair(+1, 0),
        Pair(+1, +1),
        Pair(+1, -1)
    )
    val adjacentSeats = directions.mapNotNull { direction ->
        var x1 = x
        var y1 = y
        var adjacentSeat: Char? = '.'
        do {
            y1 += direction.first
            x1 += direction.second
            adjacentSeat = seats.getOrNull(y1)?.getOrNull(x1)
        } while (adjacentSeat == '.')
        adjacentSeat
    }

    return adjacentSeats
        .filter { it == '#' }
}

