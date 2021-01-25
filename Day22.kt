import java.io.File

fun main() {
    val input = File("src/input_day_22")
        .readLines()

    val player1 = input
        .subList(1, 26)
        .map { it.toInt() }
        .toMutableList()

    val player2 = input
        .subList(28, 53)
        .map { it.toInt() }
        .toMutableList()

    if (playRound(player1, player2)) {
        println(countScore(player1))
    } else {
        println(countScore(player2))
    }
}

private fun playRound(player1: MutableList<Int>, player2: MutableList<Int>): Boolean {
    val infiniteCheck = mutableSetOf<String>()

    while (player1.isNotEmpty() && player2.isNotEmpty()) {
        val state = getState(player1, player2)
        if (!infiniteCheck.add(state)) {
            return true
        }

        val top1 = player1.removeAt(0)
        val top2 = player2.removeAt(0)

        val play1win = if (top1 <= player1.size && top2 <= player2.size) {
            val snap1 = mutableListOf<Int>()
            snap1.addAll(player1.take(top1))
            val snap2 = mutableListOf<Int>()
            snap2.addAll(player2.take(top2))
            playRound(snap1, snap2)
        } else {
            top1 > top2
        }

        if (play1win) {
            player1.add(top1)
            player1.add(top2)
        } else {
            player2.add(top2)
            player2.add(top1)
        }
    }

    return player1.isNotEmpty()
}

private fun countScore(list: List<Int>): Int {
    var score = 0
    var multiplier = list.size
    for (item in list) {
        score += multiplier * item
        multiplier -= 1
    }
    return score
}

private fun getState(player1: List<Int>, player2: List<Int>): String {
    return "$player1@$player2"
}