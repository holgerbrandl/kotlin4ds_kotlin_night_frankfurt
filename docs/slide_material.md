ongoing ide annoyances



## data vis

com.twosigma.beakerx.chart.xychart.Plot;



## examples


```r
require(dplyr)
require(stringr)
require(tidyr)

df = frame_data(
~ x,
"num_classes=10,batch_size=32,version=1,n=3,epochs=200,data_augmentation=True,subtract_pixel_mean=True,checkpoint_epochs=False",
"num_classes=11,batch_size=33,version=3,n=4,epochs=200,data_augmentation=True,subtract_pixel_mean=True,checkpoint_epochs=False"
)

mutate(df, pairs = str_split(x, ",")) %>%
    unnest(pairs) %>%
    separate(pairs, c("key", "value"), sep = "=") %>%
    spread(key, value)

```


---
### too many methods in namespace

https://blog.kotlin-academy.com/effective-java-in-kotlin-item-1-consider-static-factory-methods-instead-of-constructors-8d0d7b5814b2

> The problem with such solution is that while public top-level functions are available everywhere, it is really easy to litter user IDE tips. The even bigger problem starts when someone is creating top-level functions with names that are not directly pointing that it is not a method.

> Object creation using top-level functions is a perfect choice for small and commonly created objects, like `List` or `Map`, because `listOf(1,2,3)` is simpler and more readable then `List.of(1,2,3)` (even though it looks similar). Although public top-level functions need to be used carefully and deliberately.


--
## Tidy Data

![](.slide_material_images/tidy_data.png)


source: http://jules32.github.io/2016-07-12-Oxford/dplyr_tidyr/#2_dplyr_overview



steal from http://holgerbrandl.github.io/krangl/bier_slides_june2016/krangl_intro.html#2


--

Pandas Examples:
https://www.analyticsvidhya.com/blog/2016/01/12-pandas-techniques-python-data-manipulation/
