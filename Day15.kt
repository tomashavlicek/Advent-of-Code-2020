import kotlin.time.measureTime

fun main() {

//    val numbers = mutableListOf(0,3,6)
//
//    while (numbers.size < 30000001) {
//        val last = numbers.last()
//
//        if (numbers.subList(0, numbers.size - 1).contains(last)) {
//            val a = numbers.indexOfLast { it == last }
//            numbers.add(a - numbers.subList(0, a).indexOfLast { it == last })
//        } else {
//            numbers.add(0)
//        }
//    }
//
//    println(numbers[30000000])

    val input = "0,20,7,16,1,18,15".split(",").map { it.toInt() }.toMutableList()

    val lastIndex = IntArray(30_000_000) { -1 }
    for (i in input.indices) lastIndex[input[i]] = i

    var turns = input.size + 1
    var lastSpoken = 0

    val time = measureTime {
        while (turns < 30_000_000) {
            val lastSpokenIndex = lastIndex[lastSpoken]
            lastIndex[lastSpoken] = turns - 1
            lastSpoken = if (lastSpokenIndex == -1) 0 else turns - 1 - lastSpokenIndex
            turns++
        }
    }

    println(time.inSeconds)
    println(lastSpoken)

//    val sample = lines.first().split(",").map { it.toInt() }.toMutableList()
//    val map = hashMapOf<Int, Int>()
//    for (i in sample.indices) map[sample[i]] = i
//
//    var turns = sample.size + 1
//    var lastSpoken = 0
//
//    val time = measureTime {
//        while (turns < 30_000_000) {
//            if (map[lastSpoken] == null) {
//                map[lastSpoken] = turns - 1
//                lastSpoken = 0
//            } else {
//                val num = turns - 1 - map[lastSpoken]!!
//                map[lastSpoken] = turns - 1
//                lastSpoken = num
//            }
//            turns++
//        }
//    }
//
//    println(time.inMilliseconds)
//    println(lastSpoken)
}

//129262