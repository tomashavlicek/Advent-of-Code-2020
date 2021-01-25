import java.io.File

fun main() {
    val input = File("src/input_day_14")
        .readLines()

//    val memory = mutableMapOf<Long, Long>()
//    var maskAnd = 0L
//    var maskOr = 0L
//
//    for (line in input) {
//        if (line.take(7) == "mask = ") {
//            val mask = line.drop(7)
//            maskAnd = mask.replace("X", "1").toLong(radix = 2)
//            maskOr = mask.replace("X", "0").toLong(radix = 2)
//        } else {
//            val (position, value) = "\\d+".toRegex().findAll(line).map { it.value.toLong() }.toList()
//            memory[position] = (value and maskAnd) or maskOr
//        }
//    }
//    println(memory.values.sum())


    val mem = mutableMapOf<Long, Long>()
    var (maskOr) = listOf(0L, 0L)
    var mask = ""
    for(s in input) {
        if (s.take(7) == "mask = ") {
            mask = s.drop(7)
            maskOr = mask.replace("X", "0").toLong(2)
        }
        if (s.take(4) == "mem[") {
            var (loc, value) = "\\d+".toRegex().findAll(s).map { it.value.toLong() }.toList()
            val floatingBits = mask.mapIndexed { index, c -> index to c }.filter { it.second == 'X' }
            loc = loc or maskOr
            for(i in (0 until (1 shl floatingBits.size))) {
                val bits = i.toString(2).padStart(floatingBits.size, '0')
                val locArray = loc.toString(2).padStart(36, '0').toCharArray()
                bits.forEachIndexed { index, c -> locArray[floatingBits[index].first] = bits[index] }
                val address = locArray.joinToString("").toLong(2)
                mem[address] = value
            }
        }
    }
    println(mem.values.sum())
}