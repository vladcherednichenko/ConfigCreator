import file_360_entities.File360
import main_entities.Character
import java.io.File

class FileWriter (characterName: String){

    val mainFileName: String = "$characterName.xml"
    val file360Name: String = "360.xml"
    val startFileName: String = "Start.xml"
    val finishFileName: String = "Finish.xml"

    var xmlFileStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"

    fun writeCharacterToFile(character: Character, folder: String){

        cleanFolder(folder)

        writeMainFile(character, folder)
        write360File(character.file360, folder)

    }

    fun cleanFolder(folder: String){

        File(folder).listFiles().forEach { it.delete() }

    }

    fun writeMainFile(character: Character, folderName: String){


        File(folderName + File.separator + mainFileName).createNewFile()
        File(folderName + File.separator + mainFileName).writeText(xmlFileStart + character.toString())

    }

    fun write360File(file: File360, folderName: String){

        File(folderName + File.separator + file360Name).createNewFile()
        File(folderName + File.separator + file360Name).writeText(file.toString())

    }


}