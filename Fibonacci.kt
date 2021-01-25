fun main() {
    println(recursion_fibonacci(position = 9))

    println(dynamic_fibonacci(position = 9))

    println(spaceFibonacci(position = 9))

    println(matrixFibonacci(position = 9))
}

private fun recursion_fibonacci(position: Int): Int {
    return if (position <= 1) {
        position
    } else {
        recursion_fibonacci(position - 1) + recursion_fibonacci(position - 2)
    }
}

private fun dynamic_fibonacci(position: Int): Int {
    val f = mutableListOf(0, 1)
    for (i in 2..position+1) {
        f.add(i, f[i - 1] + f[i - 2])
    }
    return f[position]
}

private fun spaceFibonacci(position: Int): Int {
    var a = 0
    var b = 1
    return if (position == 0) {
        a
    } else if (position == 1) {
        b
    } else {
        for (i in 2..position+1) {
            var c = a + b
            a = b
            b = c
        }
        b
    }
}

private fun matrixFibonacci(position: Int): Int {
    val F = arrayOf(arrayOf(1, 1), arrayOf(1, 0))
    if (position == 0) {
        return 0
    }
    power(F, position - 1)

    return F[0][0]
}

private fun power(F: Array<Array<Int>>, n: Int) {
    val M = arrayOf(arrayOf(1, 1), arrayOf(1, 0))
    for (i in 2..n+1) {
        mutliply(F, M)
    }
}

private fun mutliply(F: Array<Array<Int>>, M: Array<Array<Int>>) {
    val x = (F[0][0] * M[0][0] +
            F[0][1] * M[1][0])
    val y = (F[0][0] * M[0][1] +
            F[0][1] * M[1][1])
    val z = (F[1][0] * M[0][0] +
            F[1][1] * M[1][0])
    val w = (F[1][0] * M[0][1] +
            F[1][1] * M[1][1])
    F[0][0] = x
    F[0][1] = y
    F[1][0] = z
    F[1][1] = w
}
