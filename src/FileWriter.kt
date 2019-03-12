import file_360_entities.File360
import finish_entities.Finish
import main_entities.ClayCharacter
import start_entities.Start
import java.io.File

class FileWriter (characterName: String){

    val mainFileName: String = "$characterName.xml"
    val file360Name: String = "360.xml"
    val startFileName: String = "Start.xml"
    val finishFileName: String = "Finish.xml"

    var xmlFileStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"

    fun writeCharacterToFile(clayCharacter: ClayCharacter, folder: String){

        cleanFolder(folder)

        writeMainFile(clayCharacter, folder)
        write360File(clayCharacter.file360, folder)
        writeFinishFile(clayCharacter.finish, folder)
        writeStartFile(clayCharacter.start, folder)

    }

    fun cleanFolder(folder: String){

        File(folder).listFiles().forEach { it.delete() }

    }

    fun writeMainFile(clayCharacter: ClayCharacter, folderName: String){


        File(folderName + File.separator + mainFileName).createNewFile()
        File(folderName + File.separator + mainFileName).writeText(xmlFileStart + clayCharacter.toString())

    }

    fun write360File(file: File360, folderName: String){

        File(folderName + File.separator + file360Name).createNewFile()
        File(folderName + File.separator + file360Name).writeText(file.toString())

    }

    fun writeFinishFile(finishFile: Finish, folderName: String){

        File(folderName + File.separator + finishFileName).createNewFile()
        File(folderName + File.separator + finishFileName).writeText(finishFile.toString())

    }

    fun writeStartFile(startFile: Start, folderName: String){

        File(folderName + File.separator + startFileName).createNewFile()
        File(folderName + File.separator + startFileName).writeText(startFile.toString())

    }




}