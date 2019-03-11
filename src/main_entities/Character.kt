package main_entities

import file_360_entities.File360

class Character {

    var name : String = ""

    var parts = ArrayList<BodyPart>()

    var file360 = File360()

    override fun toString(): String{

        var result = "<Figure name=\"$name\" anchorPointX=\"0.5\" anchorPointY=\"0\">\n"

        parts.forEach { result += it.toString() }

        result += "</Figure>"

        return result

    }

}