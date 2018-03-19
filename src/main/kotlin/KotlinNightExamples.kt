import krangl.*

/**
 * @author Holger Brandl
 */
fun main(args: Array<String>) {


//    groupingExample()
    apiIssues()

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

    // hidden
    val users = usersDF.rowsAs<User>()
    // hidden



    val groupBy: Map<Int, List<User>> = users.groupBy { it.age }
    groupBy.map { it.value.size } // #  users with same age

    // https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-grouping/
    val groupingBy = users.groupingBy { it.age }
    groupingBy.eachCount()

    val complexGrouping = users.groupingBy { listOf(it.age, it.hasSudo) }
    complexGrouping.eachCount()
}


fun apiIssues() {
    val usersDF = dataFrameOf(
        "firstName", "lastName", "age", "hasSudo")(
        "max", "smith", 53, false,
        "tom", "doe", 30, false,
        "eva", "miller", 23, true,
        null, "meyer", 23, null
    )

    // hidden
    val users = usersDF.rowsAs<User>()

//    arrayOf(1,2,3)+4
    dataFrameOf("user")(users).addColumn("age_plus_3") { it["user"].map<User> { it.age } + 3 }

    // works
    usersDF.addColumn("age_plus_3") { it["age"] + 3 }
    usersDF.addColumn("row_number") { nrow }

    //


    val mean:Double = usersDF["age"].mean2
    val mean:Double = usersDF["age"].foo()
}

private fun DataCol.foo(): Double {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

private val DataCol.mean2: Double
    get() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
