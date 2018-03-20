#!/usr/bin/env bash

cd /Users/brandl/Dropbox/kotlin_night_frankfurt/src/main/kotlin/report_rendering

notedown hello_kotlin.md > hello_kotlin.ipynb

#jupyter nbconvert --ExecutePreprocessor.kernel_name=ir  --execute --to markdown ${mdBaseName}.ipynb --stdout > ${mdBaseName}.rendered.md
#jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin  --execute --to markdown hello_kotlin.ipynb --output ${mdBaseName}.rendered

jupyter nbconvert --ExecutePreprocessor.kernel_name=kotlin  --execute --to html hello_kotlin.ipynb --output hello_kotlin