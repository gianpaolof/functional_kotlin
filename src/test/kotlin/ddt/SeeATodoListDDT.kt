package ddt

import com.ubertob.pesticide.core.DDT
import ddt.tooling.ToDoListOwnerDDT
import ddt.tooling.ZettaiDDT
import ddt.tooling.allActions

/*
 Each test class has all the scenario to define a single user story. We need
to inherit from the base DDT class and pass the list of actions.
 */
class SeeATodoListDDT : ZettaiDDT(allActions()) {

    val frank by NamedActor(::ToDoListOwnerDDT)
    val bob by NamedActor(::ToDoListOwnerDDT)

    val shoppingListName = "shopping"
    val shoppingItems = listOf("carrots", "apples", "milk")

    val gardenListName = "gardening"
    val gardenItems = listOf("fix the fence", "mowing the lawn")

    @DDT
    fun `List owners can see their lists`() = ddtScenario {

        setUp {
            frank.`starts with a list`(shoppingListName, shoppingItems)
            bob.`starts with a list`(gardenListName, gardenItems)
        }.thenPlay(
            frank.`can see #listname with #itemnames`(shoppingListName, shoppingItems),
            bob.`can see #listname with #itemnames`(gardenListName, gardenItems)
        )

    }

    @DDT
    fun `Only owners can see their lists`() = ddtScenario {

        setUp {
            frank.`starts with a list`(shoppingListName, shoppingItems)
            bob.`starts with a list`(gardenListName, gardenItems)
        }.thenPlay(
            frank.`cannot see #listname`(gardenListName),
            bob.`cannot see #listname`(shoppingListName)
        )

    }

}