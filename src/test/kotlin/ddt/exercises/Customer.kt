package ddt.exercises

import com.ubertob.pesticide.core.DdtActor
import com.ubertob.pesticide.core.DdtStep
import strikt.api.expectThat
import strikt.assertions.isNotNull
import strikt.assertions.isNull

class Customer(override val name: String) : DdtActor<CashierActions>() {


    fun `can add #qty #item`(quantity: Int, item: Item) =
        step ( quantity, item ){
            val itemInTheBag =  getCustomerBag(name)
            expectThat(itemInTheBag).isNotNull()
        }
    fun `check total is #total`(total: Double): DdtStep<CashierActions, *> {
        TODO("Not yet implemented")
    }
}