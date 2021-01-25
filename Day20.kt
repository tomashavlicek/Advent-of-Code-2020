import java.io.File

fun main() {
    val icounts = mutableMapOf<Int, Int>()
    for (t in tiles) {
        for (i in t.edgeVals) {
            icounts.merge(i, 1) { o, n ->
                o + n
            }
        }
    }

    var ans = 1L
    for (t in tiles) {
        var cnt = 0
        for (i in t.edgeVals) {
            if (icounts[i]!! == 1) {
                cnt++
            }
        }
        if (cnt > 2) {
            ans *= t.id
        }
    }
    println(ans)
}

private val tiles = File("src/input_day_20")
    .readText()
    .split("\n\n")
    .map { Tile(it) }

private val nessie = File("src/input_day_20_nessie")
    .readLines()

private data class Tile(var tile: String) {


    var id: Int
    var data: List<List<Boolean>>
    val edgeVals: MutableList<Int>

    init {
        val lines = tile.split("\n")
        val idRegex = """Tile (\d+):""".toRegex()
        val (temp) = idRegex.matchEntire(lines.first())!!.destructured
        id = temp.toInt()

        data = lines.drop(1).map { line ->
            line.map { row ->
                row == '#'
            }
        }
//        println(data)

        val d = mutableListOf<Boolean>()
        edgeVals = mutableListOf()
        for (i in data.indices) {
            d.add(data[i][0])
        }
        edgeVals.addAll(getVals(d))
        for (i in data.indices) {
            d[i] = data[i][9]
        }
        edgeVals.addAll(getVals(d))
        for (i in data.indices) {
            d[i] = data[0][i]
        }
        edgeVals.addAll(getVals(d))
        for (i in data.indices) {
            d[i] = data[9][i]
        }
        edgeVals.addAll(getVals(d))
    }


    private fun getVals(d: List<Boolean>): List<Int> {
        var str = ""
        for (i in d.indices) {
            str += if (d[i]) "1"
            else "0"
        }

        val rv = mutableListOf<Int>()
        rv.add(str.toInt(radix = 2))
        rv.add(str.reversed().toInt(radix = 2))

        return rv
    }

}