//' ## Flowers Analysis

//' The iris flower:
//' ![](https://goo.gl/tTbZMq)

@file:MavenRepository("bintray-plugins","http://jcenter.bintray.com")
@file:DependsOnMaven("de.mpicbg.scicomp:krangl:0.7")

import krangl.*



//' the input data
irisData

//' structur of the input data
irisData.glimpse()

//' Calculate mean petal width
val summarizeDf: DataFrame = irisData
    .groupBy("Species")
    .summarize("mean_petal_width") { it["Petal.Width"].mean() }

summarizeDf.print()

//' conclusion: Iris flowers of species _virginica_ have on average largest petal width.