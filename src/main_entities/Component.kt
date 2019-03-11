package main_entities

class Component {

    var type = ""

    var images: ArrayList<String> = ArrayList()

    override fun toString(): String{

        var result = "\t\t\t<Component type=\"$type\" time=\"0.8\" interval=\"0.04\" startTime=\"1\" pauseAfterFrame=\"0\" pauseTime=\"0\" startHopTime=\"0\">\n"

        images.forEach {result += "\t\t\t\t<img src=\"$it\" />"}

        result += "\t\t\t</Component>"

        return result

    }

}