import krangl.*
import java.util.*

/**
 * @author Holger Brandl
 */
fun main(args: Array<String>) {


    //    groupingExample()
    //    tidyExamples()
//    jupyterExample()
    reshapingExamples()
    //    apiIssues()

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

    //    val mean:Double = usersDF["age"].mean2
    //    val mean:Double = usersDF["age"].foo()
}

fun tidyExamples() {
    dataFrameOf("user")("brandl,holger,38")
        .apply { print() }
        .separate("user", listOf("last_name", "first_name", "age"), convert = true)
        .apply { print() }
        .apply { glimpse() }

}


fun lambdaBasic() {
    fun lazyMsg(condition: Boolean, msg: (Date) -> String) {
        if (condition) println(msg(Date()))
    }

    lazyMsg(true, { "huhu"})
    lazyMsg(true, { occurred ->"huhu + ${occurred}"})
}

fun addColumnDesign() {
    val df = dataFrameOf()()

    df.addColumn("foo"){ it.df.filter { it["foo"] gt 3 } }
}

fun reshapingExamples() {
    val climate = dataFrameOf(
        "city", "coast_distance", "1995", "2000", "2005")(
        "Dresden", 400, 343, 252, 423,
        "Frankfurt", 534, 534, 435, 913)

    climate.print()

    climate.
        gather("year", "rainfall", columns = { matches("[0-9]*")} ).
        print()
}


fun jupyterExample() {
    //    @file:MavenRepository("bintray-plugins","http://jcenter.bintray.com")
    //    @file:DependsOnMaven("de.mpicbg.scicomp:krangl:0.7")

    irisData

    irisData.glimpse()

    val summarizeDf: DataFrame = irisData
        .groupBy("Species")
        .summarize("mean_petal_width") { it["Petal.Width"].mean() }

    summarizeDf.print()

}

private fun DataCol.foo(): Double {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}

private val DataCol.mean2: Double
    get() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
