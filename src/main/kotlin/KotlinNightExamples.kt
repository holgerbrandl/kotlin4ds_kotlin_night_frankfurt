@file:Suppress("RemoveRedundantBackticks")

import krangl.*
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix
import org.apache.commons.math3.stat.inference.TestUtils
import org.apache.commons.math3.stat.regression.SimpleRegression
import java.io.File
import java.util.*

/**
 * @author Holger Brandl
 */
fun main(args: Array<String>) {


    //    groupingExample()
    //    tidyExamples()
    //    jupyterExample()
    //    reshapingExamples()
//        typeSupport()
//        irisFromSchema()
    //    allTogether()
    //    ttest()
    linRegression()
    //    apiIssues()

}

fun irisFromSchema() {
    data class Iris(val sepalLength: Double, val sepalWidth: Double, val petalLength: Double,
                    val petalWidth: Double, val species: String)

    val records: Iterable<Iris> = irisData.rowsAs<Iris>()
    print(records.take(2))
}

fun allTogether() {
    flightsData
        .groupBy("year", "month", "day")
        .select({ range("year", "day") }, { listOf("arr_delay", "dep_delay") })
        .summarize(
            "mean_arr_delay" `=` { it["arr_delay"].mean(removeNA = true) },
            "mean_dep_delay" to { it["dep_delay"].mean(removeNA = true) }
        )
        .filter { (it["mean_arr_delay"] gt 30) OR (it["mean_dep_delay"] gt 30) }
        .sortedBy("mean_arr_delay")
        .print()
}

fun typeSupport() {
    data class Person(val name: String, val age: Int)

    val persons: List<Person> = listOf(Person("Anna", 23), Person("Anna", 43))

    // convert collections into data-frames
    val personsDF: DataFrame = persons.asDataFrame()
    personsDF.print()


    // convert collections into data-frames
    val personsDF2: DataFrame = dataFrameOf("person")(persons)
    print(personsDF2)
    personsDF2.glimpse()

    //    personsDF2.unfold<Person>("person", keep=true)

    //    val personsRestored :Iterable<Person> = df

    // print data class schema for table


    // part 3
    //    irisData.printDataClassSchema("Iris")


}

data class User(val firstName: String?, val lastName: String, val age: Int, val hasSudo: Boolean?)


private fun groupingExample() {
    File(".").readBytes()
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
    val groupingBy: Grouping<User, Int> = users.groupingBy { it.age }
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

    lazyMsg(true, { "huhu" })
    lazyMsg(true, { occurred -> "huhu + ${occurred}" })
}

fun addColumnDesign() {
    val df = dataFrameOf()()

    df.addColumn("foo") { it.df.filter { it["foo"] gt 3 } }
}

fun reshapingExamples() {
    val climate = dataFrameOf(
        "city", "coast_distance", "1995", "2000", "2005")(
        "Dresden", 400, 343, 252, 423,
        "Frankfurt", 534, 534, 435, 913)

    climate.print()

    climate.gather("year", "rainfall", columns = { matches("[0-9]*") }).print()
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


data class Foo(val bar: String)

infix operator fun Foo.compareTo(rightCol: Foo) = -1
//infix operator fun Foo.(rightCol:Foo) = -1


fun future() {
    Foo("1") > Foo("2")

}


fun linRegression() {

    val irisModel = irisData
        .groupBy("Species")
        .summarize("lm") {
            val x = it["Sepal.Length"].asDoubles().filterNotNull().toDoubleArray()
            val y = it["Sepal.Width"].asDoubles().filterNotNull().toDoubleArray()

            val xTransposed = MatrixUtils.createRealMatrix(arrayOf(x)).transpose().data
            SimpleRegression().apply { addObservations(xTransposed, y) }

        }
//        .addColumns(
//            "slope" to { it["lm"].map<SimpleRegression> { it.slope } },
//            "intercept" to { it["lm"].map<SimpleRegression> { it.intercept } }
//        )
        .unfold<SimpleRegression>("lm", properties = listOf("intercept", "slope"))

    irisModel.print()
    irisModel.glimpse()


}