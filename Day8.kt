import java.io.File

private val input = File("src/input_day_8")
    .readText()
    .split("\n")
    .map { s: String ->
        val (a, b) = s.split(" ", limit = 2)
        Instruction(a, b.toInt())
    }
var accumulator = 0

fun main() {
//    println(run().also { println(accumulator) })

    sol2().also { println(accumulator) }

}

fun sol2() = input.forEach { op ->
    val origOp = op.name
    when (op.name) {
        "jmp" -> op.name = "nop"
        "nop" -> op.name = "jmp"
    }
    when (run()) {
        -1 -> op.name = origOp
        else -> return
    }
}

fun run(): Int {
    accumulator = 0
    var line = 0
    val loopCheck = mutableSetOf<Int>()
    while (true) {
        val instruction = input[line]
        if (instruction.name == "acc") {
            accumulator += instruction.value
            line += 1
        } else if (instruction.name == "jmp") {
            line += instruction.value
        } else if (instruction.name == "nop") {
            line += 1
        }

        if (loopCheck.contains(line)) {
            return -1
        } else if (line == input.size) {
            return 0
        } else {
            loopCheck.add(line)
        }
    }
}

data class Instruction(var name: String, var value: Int)