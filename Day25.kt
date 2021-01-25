fun main() {
    solvePart1()
}

private val input = "13316116,13651422".split(',')
private val cardPublicKey = input.first().toLong()
private val doorPublicKey = input.last().toLong()

private fun Long.mathPart(subject: Long = 7L): Long =
    this * subject % 20201227

private fun findLoopSize(target: Long): Int =
    generateSequence(1L) { it.mathPart() }.indexOf(target)

private fun transform(loopSize: Int, subject: Long): Long =
    generateSequence(1L) { it.mathPart(subject) }.drop(loopSize).first()

private fun solvePart1() {
    println(transform(findLoopSize(doorPublicKey), cardPublicKey))
}

