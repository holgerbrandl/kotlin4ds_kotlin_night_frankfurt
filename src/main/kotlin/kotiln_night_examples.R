# Title     : TODO
# Objective : TODO
# Created by: brandl
# Created on: 3/19/18

devtools::source_url("https://raw.githubusercontent.com/holgerbrandl/datautils/v1.46/R/core_commons.R")

require(corrr)

iris %>% select_if(is.numeric) %>%correlate()
