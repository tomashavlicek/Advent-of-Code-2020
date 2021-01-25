
fun main() {
    val lines = generateSequence {
        readLine()
    }

    val pattern = """(\d+)-(\d+) (\p{Print}): (\p{Print}*)""".toRegex()

    val passwords = lines.toList().map {
        val (l, h, x, s) = pattern.matchEntire(it)!!.destructured
        Password(l.toInt(), h.toInt(), x.single(), s)
    }

    fun part1(): Int = passwords.count { (l, h, x, s) ->
        s.count { it == x } in l..h
    }

    fun part2(): Int = passwords.count { (l, h, x, s) ->
        (s[l - 1] == x) != (s[h - 1] == x)
    }
}

data class Password(val l: Int, val h: Int, val x: Char, val s: String) {
//    fun isValid(): Boolean {
//        return p.count { it == x } in l..h part 1
//        return if (p.length >= h) {
//            p[l - 1] == x && p[h - 1] != x ||
//                    p[l - 1] != x && p[h - 1] == x
//        } else {
//            p[l - 1] == x
//        }
//    } part 2
}