
//' prepare the environment
@file:MavenRepository("bintray-plugins","http://jcenter.bintray.com")
@file:DependsOnMaven("de.mpicbg.scicomp:krangl:0.7")

package report_rendering


import MavenRepository
import DependsOnMaven
import krangl.*


//' glimpse into the data
irisData

irisData.glimpse()

//' Calculate mean by species
val summarizeDf: DataFrame = irisData
    .groupBy("Species")
    .summarize("mean_petal_width") { it["Petal.Width"].mean() }

summarizeDf.print()