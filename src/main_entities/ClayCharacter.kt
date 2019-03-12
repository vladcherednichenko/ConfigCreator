package main_entities

import file_360_entities.File360
import finish_entities.Finish
import start_entities.Start

class ClayCharacter {

    var name : String = ""

    var parts = ArrayList<BodyPart>()

    var file360 = File360()

    var finish = Finish()

    var start = Start(name)

    override fun toString(): String{

        var result = "<Figure name=\"$name\" anchorPointX=\"0.5\" anchorPointY=\"0\">\n"

        parts.forEach { result += it.toString() }

        result += "</Figure>"

        return result

    }

}