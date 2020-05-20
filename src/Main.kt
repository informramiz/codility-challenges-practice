import problems.CaterPillarMethod
import kotlin.math.min

fun roof(cars: Array<Long>, k: Int): Long {
    cars.sort()

    if (cars.size < k)
        return -1

    val nextCarIndexDistance = k - 1
    var minLength = Long.MAX_VALUE
    for (i in 0..cars.lastIndex-nextCarIndexDistance) {
        val length = cars[i+nextCarIndexDistance] - cars[i] + 1
        if (length >= k) {
            minLength = min(minLength, length)
        }
    }
    return minLength
}

fun main() {
    val k = 3
    val cars = arrayOf(6L, 2, 7, 12)
    val length = roof(cars, k)
    println(length)
}