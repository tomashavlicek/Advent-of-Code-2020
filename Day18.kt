import java.io.File
import java.util.*
import javax.xml.stream.events.Characters

// Reverse polish notation -> Shunting-yard algorithm

fun main() {
    val input = File("src/input_day_18")
        .readLines()
        .map { it.filter { it != ' ' } }

    println(input.map { solvePart1(it.iterator()) }.sum())

    println(input.map { solvePart2(it.iterator()) }.sum())
}

fun solvePart1(equation: CharIterator): Long {
    val numbers = mutableListOf<Long>()
    var operation = '+'

    loop@ while (equation.hasNext()) {
        when (val next = equation.next()) {
            '(' -> numbers += solvePart1(equation)
            ')' -> break@loop
            in setOf('+', '*') -> operation = next
            else -> numbers += Character.getNumericValue(next).toLong()
        }
        if (numbers.size == 2) {
            val a = numbers.removeAt(numbers.size - 1)
            val b = numbers.removeAt(numbers.size - 1)
            numbers += if (operation == '+') a + b else a * b
        }
    }

    return numbers.first()
}

private fun solvePart2(equation: CharIterator): Long {
    val multiplyThese = mutableListOf<Long>()
    var added = 0L
    loop@ while (equation.hasNext()) {
        val next = equation.nextChar()
        when {
            next == '(' -> added += solvePart2(equation)
            next == ')' -> break@loop
            next == '*' -> {
                multiplyThese += added
                added = 0L
            }
            next.isDigit() -> added += Character.getNumericValue(next).toLong()
        }
    }
    return (multiplyThese + added).reduce { a, b -> a * b}
}

