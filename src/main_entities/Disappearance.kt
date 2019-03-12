package main_entities

class Disappearance {

    var components : ArrayList<Component> = ArrayList()

    override fun toString(): String{

        var result = "\t\t<Disappearance rotate=\"false\">\n" +
                "\t\t\t<Audio>\n" +
                "\t\t\t</Audio>\n"

        components.forEach { result += it.toString() }

        result += "\t\t</Disappearance>\n"

        return result

    }


}