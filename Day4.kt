fun main() {
    val lines = generateSequence {
        readLine()
    }

    val data: Sequence<Map<String, String>> = sequence {
        var m = mutableMapOf<String, String>()
        for (line in lines) {
            if (line.isEmpty()) {
                yield(m)
                m = mutableMapOf()
            } else {
                for (word in line.splitToSequence(" ")) {
                    val (key, value) = word.split(":", limit = 2).takeIf { it.size == 2 } ?: continue
                    m[key] = value
                }
            }
        }
        yield(m)
    }

    // part 1
//    println(data.count { m ->
//        arrayOf("hgt", "byr", "iyr", "eyr", "hcl", "ecl", "pid").all { it in m }
//    })

    // part 2
    println(data.count { m ->
        m["byr"]?.toIntOrNull()?.let { it in 1920..2002 } == true &&
                m["iyr"]?.toIntOrNull()?.let { it in 2010..2020 } == true &&
                m["eyr"]?.toIntOrNull()?.let { it in 2020..2030 } == true &&
                m["hgt"]?.run {
                    endsWith("cm") && dropLast(2).toIntOrNull()?.let { it in 150..193 } == true ||
                            endsWith("in") && dropLast(2).toIntOrNull()?.let { it in 59..76 } == true
                } == true &&
                m["hcl"]?.run {
                    length == 7 && first() == '#' && drop(1).all { it in "0123456789abcdef" }
                } == true &&
                m["ecl"] in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") &&
                m["pid"]?.run {
                    length == 9 && all { it in "0123456789" }
                } == true
    })
}
