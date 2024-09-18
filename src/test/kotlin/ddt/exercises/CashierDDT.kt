package ddt.exercises

import com.ubertob.pesticide.core.*

/*
INCOMPLETE
just to gain more confidence with the pesticide ddt lib
*/


typealias CustomerBag = MutableList<Item>
typealias CustomerBags = MutableMap<String, CustomerBag>
enum class Item { carrot, milk }
interface CashierActions : DdtActions<DdtProtocol> {
    fun setupPrices(prices: Map<String, Double>)
    fun totalFor(actorName: String): Double
    fun addItem(actorName: String, qty: Int, item: Item)

    fun getCustomerBag(user: String) : CustomerBag?
    fun Customer.`has en empty bag`()
}

class CashierDomainOnlyActions : CashierActions {
    private val bags: CustomerBags = mutableMapOf()
    private val hub = CashierHub(bags)

    override fun setupPrices(prices: Map<String, Double>) {
        hub.setupPrices(prices)
    }

    override fun totalFor(actorName: String): Double {
      return hub.totalFor(actorName)
    }

    override fun addItem(actorName: String, qty: Int, item: Item) {
        hub.addItem(actorName, qty, item)
    }

    override fun getCustomerBag(user: String): CustomerBag? {
        return bags[user]
    }

    override fun Customer.`has en empty bag`() {
        val bag : CustomerBag = mutableListOf()
        bags[name] = bag
    }


    override val protocol: DdtProtocol = DomainOnly

    override fun prepare() = Ready

}
fun allcashierActions() = setOf(
    CashierDomainOnlyActions(),
)

typealias CashierDDT = DomainDrivenTest<CashierActions>
class CashierDDT2 : CashierDDT(allcashierActions()) {
    val alice by NamedActor(::Customer)

    @DDT
    fun `customer can buy an item`() = ddtScenario {
        val prices = mapOf(Item.carrot.toString() to 2.0, Item.milk.toString() to 5.0)
        setUp {
            setupPrices(prices)
            alice.`has en empty bag`()
        }.thenPlay(
            alice.`can add #qty #item`(3, Item.carrot),
            //alice.`can add #qty #item`(1, Item.milk),
            //alice.`check total is #total`(11.0)
        )
    }
}