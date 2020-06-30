import images_finder.ImageFinder

object Main {

    var outputFolder = "./result"
    var inputFolder = "./character"

    @JvmStatic
    fun main(args: Array<String>) {

        val reader = Reader(inputFolder)

        reader.read()

        var character = reader.createCharacter()

        var writer = FileWriter(character.name)
        writer.writeCharacterToFile(character, outputFolder)


//        var imagesFinder = ImageFinder()
//
//        imagesFinder.findImages()

    }


}
