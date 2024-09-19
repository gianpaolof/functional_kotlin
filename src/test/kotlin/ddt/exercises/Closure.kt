package ddt.exercises/*
    apiCall -> is part of the lambda function that createRateLimiter returns. This lambda represents a rate-limited version of your API call logic.

    The parameter passed to createRateLimiter (let's call it apiCallFunction for clarity) is the actual API call logic you want to execute, but in a rate-limited manner.

    When you use the returned lambda function (e.g., limitedApiCall), you provide apiCallFunction as an argument to it.

In simpler terms:

    createRateLimiter gives you a special function (the lambda).
    This special function takes your API call logic (apiCallFunction) as input.
    Inside the special function, apiCall acts as a placeholder or alias for apiCallFunction.
    The special function then handles rate limiting and eventually executes apiCall (which is essentially your apiCallFunction).
 */
fun createRateLimiter(maxRequests: Int, timeWindowMillis: Long): (suspend () -> Unit) -> suspend () -> Unit {
    var remainingRequests = maxRequests
    var resetTime = System.currentTimeMillis() + timeWindowMillis

    //{ apiCall -> ... } : This is the lambda function itself.
    //it is returned from createRateLimiter
    return { apiCall ->
        {
            if (System.currentTimeMillis() > resetTime) {
                remainingRequests = maxRequests
                resetTime = System.currentTimeMillis() + timeWindowMillis
            }

            if (remainingRequests > 0) {
                remainingRequests--
                apiCall()
            } else {
                println("Rate limit exceeded. Please try again later.")
            }
        }
    }
}

interface Clock {
    fun currentTimeMillis(): Long
}

fun createRateLimiter(maxRequests: Int, timeWindowMillis: Long, clock: Clock = SystemClock): (suspend () -> Unit) -> suspend () -> Unit {
    var remainingRequests = maxRequests
    var resetTime = clock.currentTimeMillis() + timeWindowMillis

    return { apiCallFunction ->
        {
            if (clock.currentTimeMillis() > resetTime) {
                remainingRequests = maxRequests
                resetTime = clock.currentTimeMillis() + timeWindowMillis
            }

            if (remainingRequests > 0) {
                remainingRequests--
                apiCallFunction()
            } else {
                println("Rate limit exceeded. Please try again later.")
            }
        }
    }
}

// Default implementation using the system clock
object SystemClock : Clock {
    override fun currentTimeMillis() = System.currentTimeMillis()
}

//createRateLimiter gives you a special function (the lambda).

val apiRateLimiter = createRateLimiter(maxRequests = 10, timeWindowMillis = 60000) // 10 requests per minute

// Creating the rate limiter (using the default SystemClock)
// Creating the rate limiter, explicitly passing SystemClock
val apiRateLimiter2 = createRateLimiter(maxRequests = 10, timeWindowMillis = 60000, clock = SystemClock)

///the api
fun fetchWeatherData(): Unit {
    // Simulate an API call to fetch weather data
    val weatherData = "Sunny, 25°C"
    println("Fetched weather data: $weatherData")
}

fun fetchWeatherData2(): Unit {
    // Simulate an API call to fetch weather data
    val weatherData = "Cloudy, 25°C"
    println("Fetched weather data: $weatherData")
}

//This special function takes your API call logic (apiCallFunction) as input.
val limitedFetchWeather = apiRateLimiter {
    fetchWeatherData()
}

val limitedFetchWeatherClock = apiRateLimiter2 {
    fetchWeatherData2()
}