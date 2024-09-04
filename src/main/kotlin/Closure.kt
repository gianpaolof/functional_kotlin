/*
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

//createRateLimiter gives you a special function (the lambda).

val apiRateLimiter = createRateLimiter(maxRequests = 10, timeWindowMillis = 60000) // 10 requests per minute

///the api
fun fetchWeatherData(): Unit {
    // Simulate an API call to fetch weather data
    val weatherData = "Sunny, 25°C"
    println("Fetched weather data: $weatherData")
}

//This special function takes your API call logic (apiCallFunction) as input.
val limitedFetchWeather = apiRateLimiter {
    fetchWeatherData()
}