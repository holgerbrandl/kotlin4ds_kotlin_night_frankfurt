#!/usr/bin/env bash

cd /Users/brandl/Dropbox/kotlin_night_frankfurt/src/main/kotlin/report_rendering

notedown hello_kotlin.md > hello_kotlin.ipynb

#jupyter nbconvert --ExecutePreprocessor.kernel_name=ir  --execute --to markdown ${mdBaseName}.ipynb --stdout > ${mdBaseName}.rendered.md
#jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin  --execute --to markdown hello_kotlin.ipynb --output ${mdBaseName}.rendered

jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin --execute --to html hello_kotlin.ipynb --output hello_kotlin


## start from kts


inputScript=krangl_example_report.kts
#inputScript=basic_kotlin_report.kts
inputBaseName=$(basename $inputScript .kts)

Rscript - ${inputScript} <<"EOF"
knitr::spin(commandArgs(T)[1], doc = "^//'[ ]?", knit=F)
EOF

kscript -t 'lines.map { it.replace("{r }", "")}.print()' ${inputBaseName}.Rmd > ${inputBaseName}.md

notedown ${inputBaseName}.md > ${inputBaseName}.ipynb

jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin --execute --to html ${inputBaseName}.ipynb --output ${inputBaseName}
