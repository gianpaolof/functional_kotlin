

suspend fun main() {

    // Simulate multiple API calls
    repeat(15) {
        limitedFetchWeather()
    }
}

