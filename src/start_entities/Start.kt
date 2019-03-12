package start_entities

class Start (name: String){

    var characterName = name

    override fun toString(): String{

        return "<Start animal=\"$characterName\" anchorPointX=\"0.5\" anchorPointY=\"0.5\" persOffsetX=\"-15\" persOffsetY=\"0\">\n" +
                "\t<Color hex=\"25CB89\"/>\n" +
                "\t<Clays anchorPointY=\"0.5\" scale=\"1\">\n" +
                "\t</Clays>\n" +
                "\t<Audio>\n" +
                "\t\t<Start>\n" +
                "\t\t</Start>\n" +
                "\t\t<Clay>\n" +
                "\t\t</Clay>\n" +
                "\t</Audio>\n" +
                "</Start>"

    }

}