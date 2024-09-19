package ddt.exercises

import org.junit.jupiter.api.Test

fun sumAndLog(a: Int, b: Int, log: (Int) -> Unit): Int = (a + b).also(log)
class HelloTest{
    @Test
    fun atest(): Unit {
        var result = sumAndLog(1,5) { print("suka") }

        println(result)
        result = sumAndLog(5, 3) { sum ->
            println("The sum is: $sum")
        }
        println(result)
    }
}
