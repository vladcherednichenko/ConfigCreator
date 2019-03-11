package main_entities

class Action {

    var type: String = ""
    var conversions: ArrayList<Conversion> = ArrayList()
    var allActionImages : ArrayList<String> = ArrayList()

    override fun toString(): String {

        var result = "\t\t<Action type=\"$type\" time=\"5\" alphaTimeForCopy=\"0.5\" handRotate=\"0\" handMirroring=\"false\" offsetX=\"0\" offsetY=\"0\">\n" +
                "\t\t\t<Audio>\n" +
                "\t\t\t</Audio>\n"

        conversions.forEach { result += it.toString() }

        result += "\t\t</Action>\n"

        return result

    }


}