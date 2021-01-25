import java.io.File
import java.lang.IllegalStateException
import kotlin.math.abs

fun main() {
//    val ship = Ship(x = 0, y = 0, z = 90)

    val input = File("src/input_day_12")
        .readLines()

    // Part 1
//    input.forEach { action: String ->
//        val a = action[0]
//        val b = action.substring(1, action.length).toInt()
//        ship.move(a, b)
//    }
//    println(abs(ship.x) + abs(ship.y))

    // Part 2
    val waypoint = Waypoint(x = 1, y = 10) // 1 N ; 10 E
    val ship = Ship(x = 0, y = 0)
    input.forEach { action: String ->
        val direction = action.take(1)
        val value = action.drop(1).toInt()
        when (direction) {
            "N" -> waypoint.x += value
            "S" -> waypoint.x -= value
            "E" -> waypoint.y += value
            "W" -> waypoint.y -= value
            "L" -> trunWaypointLeft(waypoint, (360 + value) % 360)
            "R" -> trunWaypointLeft(waypoint, (360 - value) % 360)
            "F" -> {
                ship.x += waypoint.x * value
                ship.y += waypoint.y * value
            }
            else -> throw IllegalStateException("UNKNOWN ACTION: $action")
        }
    }
    println(abs(ship.x) + abs(ship.y))
}

private fun trunWaypointLeft(waypoint: Waypoint, value: Int) {
    when (value) {
        90 -> waypoint.x = waypoint.y.also { waypoint.y = -waypoint.x }
        180 -> waypoint.x = -waypoint.x.also { waypoint.y = -waypoint.y }
        270 -> waypoint.x = -waypoint.y.also { waypoint.y = waypoint.x }
        0 -> waypoint.x = waypoint.x.also { waypoint.y = waypoint.y }
        else -> throw IllegalStateException("How to turn left: (${value})?")
    }
}

data class Waypoint(var x: Int, var y: Int)
data class Ship(var x: Int, var y: Int)


// Part 1
//data class Ship(var x: Int, var y: Int, var z: Int) {
//
//    fun move(a: Char, b: Int) {
//        when (a) {
//            'N' -> x += b
//            'S' -> x -= b
//            'E' -> y += b
//            'W' -> y -= b
//            'L' -> z = (360 + z - b) % 360
//            'R' -> z = (360 + z + b) % 360
//            'F' -> move(getOrientation(), b)
//        }
//    }
//
//    fun getOrientation(): Char {
//        return when (z) {
//            0 -> 'N'
//            90 -> 'E'
//            180 -> 'S'
//            270 -> 'W'
//            else -> throw IllegalStateException("Unsupported orientation!")
//        }
//    }
//}
