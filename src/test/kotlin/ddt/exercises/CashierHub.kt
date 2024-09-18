package ddt.exercises

class CashierHub(val bags: CustomerBags) {

    var mPrices = mapOf("" to 0.0)

    fun setupPrices(prices: Map<String, Double>) {
        mPrices= prices.toMap()
    }

    fun totalFor(actorName: String): Double {
        return calculateTotalPrice(bags[actorName], mPrices)
    }

    fun addItem(actorName: String, quantity: Int, item: Item) {
        val currentItems = bags[actorName] ?: mutableListOf()
        repeat(quantity) {
            currentItems.add(item)
        }
        bags[actorName] = currentItems
    }

    fun calculateTotalPrice(items: List<Item>?, prices: Map<String, Double>): Double {
        var totalPrice = 0.0

        if (items != null) {
            for (item in items) {
                val price = prices[item.toString()] ?: 0.0 // Default to 0.0 if price not found
                totalPrice += price
            }
        }

        return totalPrice
    }
}