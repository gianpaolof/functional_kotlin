import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class RateLimiterTest {

    @Test
    fun testRateLimiterWithinLimit() = runBlocking {
        val mockClock = mockk<Clock>()
        every { mockClock.currentTimeMillis() } returnsMany listOf(0, 5000, 10000) // Control time

        val apiRateLimiter = createRateLimiter(maxRequests = 2, timeWindowMillis = 10000, clock = mockClock)
        val limitedApiCall = apiRateLimiter {
            println("Making API call...")
        }

        limitedApiCall() // Should succeed (time = 0, remainingRequests = 2)
        limitedApiCall() // Should succeed (time = 5000, remainingRequests = 1)
        limitedApiCall() // Should fail (time = 10000, remainingRequests = 0, but time window not reset yet)

        every { mockClock.currentTimeMillis() } returns 10001 // Simulate time passing the reset window
        limitedApiCall() // Should succeed (time window reset, remainingRequests = 2)
    }
}