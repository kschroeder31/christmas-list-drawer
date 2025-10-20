fun main() {
    var iterations = 0
    do {
        iterations++
        var households = listOf(
            Household(listOf(Person("Kevin", 2), Person("Ana"))),
            household("Chad"),
            household("Holly", "Brandon"),
            household("Kyle", "Audrey"),
            household("Mary", "Rod")
        )
        var piecesOfPaper = mutableListOf<String>()
        households.forEach { household -> household.people.forEach { person -> repeat(person.entries) { piecesOfPaper.add(person.name) }}}
        piecesOfPaper.shuffle()
        households.forEach { household ->
            household.people.forEach { person ->
                repeat(person.entries) {
                    piecesOfPaper.find { chosenName ->
                        !household.allNames().contains(chosenName) && !household.allSelections().contains(chosenName) &&
                        person.selections.intersect(households.find { it.allNames().contains(chosenName) }!!.allNames()).isEmpty()
                    }?.let {
                        person.selections.add(it)
                        piecesOfPaper.remove(it)
                    }
                }
            }
        }

        if (piecesOfPaper.isEmpty()){
            households.forEach {household ->
                household.people.forEach { println("${it.name} has ${it.selections}")
                }
            }
        }
    } while ( piecesOfPaper.isNotEmpty())
    println("It took $iterations iterations.")
}

fun household(vararg peopleStrings: String) = Household(peopleStrings.map { Person(it) })
data class Household(val people: List<Person>){
    fun allNames() = people.map { it.name }
    fun allSelections() = people.flatMap { it.selections }
}

data class Person(val name: String, val entries: Int = 3, val selections: MutableList<String> = mutableListOf())
