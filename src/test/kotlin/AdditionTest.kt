import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import kotlin.random.Random

class AdditionTest {

    fun randomNatural() = Random.nextInt(from = 1, until = 100_000_000)
    @Test
    fun `add two numbers`() {
        expectThat(5 + 6).isEqualTo(11)
        expectThat(7 + 42).isEqualTo(49)
        expectThat(9999 + 1).isEqualTo(10000)
    }

    @Test
    fun `zero identity`() {
        repeat(100) {
            val x = randomNatural()
            expectThat(x + 0).isEqualTo(x)
        }
    }
    @Test
    fun `commutative property`() {
        repeat(100) {
            val x = randomNatural()
            val y = randomNatural()
            expectThat(x + y).isEqualTo(y + x)
        }
    }
}