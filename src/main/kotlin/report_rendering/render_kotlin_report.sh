#!/usr/bin/env bash

cd /Users/brandl/Dropbox/kotlin_night_frankfurt/src/main/kotlin/report_rendering

#' 1st part

notedown hello_kotlin.md > hello_kotlin.ipynb
jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin --execute --to html hello_kotlin.ipynb --output hello_kotlin

jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin --execute --to markdown hello_kotlin.ipynb --output hello_kotlin_markdown


## 2nd part: start from kts

#inputScript=basic_kotlin_report.kts

inputScript=krangl_example_report.kts
inputBaseName=$(basename $inputScript .kts)

# https://www.r-project.org/
Rscript - ${inputScript} <<"EOF"
knitr::spin(commandArgs(T)[1], doc = "^//'[ ]?", knit=F)
EOF

# https://github.com/holgerbrandl/kscript
kscript -t 'lines.map { it.replace("{r }", "")}.print()' ${inputBaseName}.Rmd > ${inputBaseName}.md

# https://github.com/aaren/notedown
notedown ${inputBaseName}.md > ${inputBaseName}.ipynb

# http://jupyter.org/install
jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin --execute --to html ${inputBaseName}.ipynb --output ${inputBaseName}
