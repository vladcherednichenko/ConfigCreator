package main_entities

class BodyPart {

    var type: String = ""

    var partNumber = ""

    var actions = ArrayList<Action>()

    var disappearance = Disappearance()

    var disapList = ArrayList<String>()

    override fun toString(): String{

        var result = "\t<Part type=\"$type\" disappearanceInterval=\"1\">\n"

        actions.forEach{result += it.toString()}

        result += disappearance.toString()

        result += "\t</Part>\n"

        return result

    }

}