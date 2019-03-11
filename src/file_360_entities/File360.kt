package file_360_entities

class File360 {

    var images360 = ArrayList<String>()

    override fun toString(): String{

        var result = "<Mode360>\n" +
                "\t<Figure360>\n"

        images360.forEach {

            result += "\t\t<img src=\"$it\" />\n"

        }

        result += "\t</Figure360>\n"

        result += "</Mode360>"

        return result

    }

}