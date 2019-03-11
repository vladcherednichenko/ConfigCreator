package main_entities

class BodyPart {

    var type: String = ""

    var actions: ArrayList<Action> = ArrayList()

    var disappearance: Disappearance = Disappearance()

    override fun toString(): String{

        var result = "\t<Part type=\"$type\" disappearanceInterval=\"1\">\n"

        actions.forEach{result += it.toString()}

        result += "\t</Part>\n"

        return result

    }

}