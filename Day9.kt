import java.io.File

fun main() {
    val input = File("src/input_day_9")
        .readText()
        .split("\n")
        .map { it.toLong() }

//    part 1
    val preamble = input.subList(0, 25).toMutableList()
    var invalidNumber = 0L

    for (i in 25 until input.size) {
        if (!preamble.any { n -> preamble.any { n + it == input[i] } }) {
            invalidNumber = input[i]
            break
        }
        preamble.removeAt(0)
        preamble.add(input[i])
    }

    println(invalidNumber)

//    part 2
    outer@for (d in 0..input.size) {
        for (e in d+1..input.size) {
            val interval = input.subList(d, e)
            if (interval.sum() == invalidNumber) {
                println(interval)
                println(interval.min()!! + interval.max()!!)
                break@outer
            }
        }
    }
}

//    var l = 0
//    var h = 25
//    var preamble = input.subList(l, h)
//    for (i in h..input.size) {
//        var test = false
//        inner@ for (d in preamble) {
//            if (preamble.contains(input[i] - d)) {
//                test = true
//                break@inner
//            }
//        }
//        if (test) {
//            l++
//            h++
//            preamble = input.subList(l, h)
//        } else {
//            println(input[i])
//            break
//        }
//    }
