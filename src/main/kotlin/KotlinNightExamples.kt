import krangl.*

/**
 * @author Holger Brandl
 */
fun main(args: Array<String>) {


    groupingExample()

}

data class User(val firstName: String?, val lastName: String, val age: Int, val hasSudo: Boolean?)


private fun groupingExample() {
    val usersDF = dataFrameOf(
        "firstName", "lastName", "age", "hasSudo")(
        "max", "smith", 53, false,
        "tom", "doe", 30, false,
        "eva", "miller", 23, true,
        null, "meyer", 23, null
    )



    val users = usersDF.rowsAs<User>()
    dataFrameOf("user")(*users.toList().toTypedArray())["user"]
    dataFrameOf("user")(users).addColumn("age_plus_3") { it["user"].map<User> { it.age } + 3 }

    val groupBy: Map<Int, List<User>> = users.groupBy { it.age }
    groupBy.map { it.value.size } // #  users with same age

    // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-grouping/
    val groupingBy = users.groupingBy { it.age }
    groupingBy.eachCount()

    val complexGrouping = users.groupingBy { listOf(it.age, it.hasSudo) }
    complexGrouping.eachCount()
}