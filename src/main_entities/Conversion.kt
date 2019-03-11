package main_entities

class Conversion {

    var tabsNumber = 3

    var images: ArrayList<String> = ArrayList()

    var conversionNumber = ""

    override fun toString(): String{

        var result = "\t\t\t<Conversion>\n" +
                "\t\t\t\t<startAlphaTime>1.5</startAlphaTime>\n" +
                "\t\t\t\t<alphaTime>0.5</alphaTime>\n" +
                "\t\t\t\t<startAlphaTimeForCopy>4</startAlphaTimeForCopy>\n"

        images.forEach { result += "\t\t\t\t<img src=\"$it\" />\n" }


        result += "\t\t\t</Conversion>\n"

        return result

    }

}