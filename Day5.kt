
fun main() {
    val lines = generateSequence {
        readLine()
    }

    val rows = mutableListOf<Int>()
    val columns = mutableListOf<Int>()
    for (i in 0..127) {
        rows.add(i, i)
    }
    for (j in 0..7) {
        columns.add(j, j)
    }

    val seats = lines.toList().map { s: String ->
        Seat(s, decode(s.substring(0, 7), rows), decode(s.substring(7, 10), columns))
    }

//    part 1
//    seats.sortedByDescending { it.id }.forEach { println(it) }

//    part 2
    val ids = seats.map { it.id }.sortedByDescending { it }
    for (i in ids.indices) {
        if (ids[i] != ids[i + 1] + 1) {
            println("Empty seat is ${ids[i] - 1}")
            break
        }
    }
}

data class Seat(val s: String, val row: Int, val column: Int) {
    val id = row * 8 + column

    override fun toString(): String {
        return "root $s -> row $row column $column id ${id}"
    }
}

fun decode(s: String, d: List<Int>): Int {
    var l = 0
    var h = d.last()
    var m = 0
    for (c in s.substring(0, s.length - 1)) {
        m = l + (h - l) / 2
        if (c == 'F' || c == 'L') {
            h = m
        } else {
            l = m + 1
        }
    }
    return if (s.last() == 'F' || s.last() == 'L') {
        l
    } else {
        h
    }
}
