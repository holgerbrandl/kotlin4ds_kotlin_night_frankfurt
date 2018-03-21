#' ## Iris Flowers

require(corrr)
require(dplyr)
require(ggplot2)

#' Do a correlation analysis
iris %>% select_if(is.numeric) %>% correlate()

#' Check for feature correlation with scatter plot
ggplot(iris, aes(Sepal.Length, Sepal.Width, colour=Species)) +
    geom_point()

# other comments