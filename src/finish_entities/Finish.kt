package finish_entities

class Finish {

    var lipsImages = ArrayList<String>()
    var animationImages = ArrayList<String>()

    override fun toString(): String{

        var res = "<Finish>\n" +
                "\t<DoneAudio>\n" +
                "\t</DoneAudio>\n" +
                "\t<LipsAudio pitch=\"-6\" speed=\"-2\">\n" +
                "\t</LipsAudio pitch=\"-6\" speed=\"-2\">\n" +
                "\t<Lips>\n"


        lipsImages.forEach {

            res += "\t\t<img src=\"$it\" />\n"

        }

        res += "\t</Lips>\n" +
                "\t<Animation>\n"

        animationImages.forEach {

            res += "\t\t<img src=\"$it\" />\n"

        }

        res += "\t</Animation>\n" +
                "</Finish>"



        return res

    }

}