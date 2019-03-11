import file_360_entities.File360
import java.io.File
import main_entities.*

class Reader(inputFolderName: String){

    val numbers = "0123456789"

    var figureName = ""

    val inputMainFolderName = inputFolderName

    val partsImagesFolderName = "/ out/Parts"
    val file360ImagesFolderName = "/ out/360"
    var constructionImagesFolder = ""

    var partsFolder = ""

    var all360Images = ArrayList<String>()
    var allPartsImages = ArrayList<String>()

    fun read(){

        readPartsImages()

        read360Images()

    }

    fun readPartsImages(){

        File(inputMainFolderName).listFiles().forEach {

            if(it.isDirectory){

                figureName = it.name
                partsFolder = "$inputMainFolderName/" + it.name + partsImagesFolderName

            }

        }

        File(partsFolder).walkTopDown().forEach {

            if(it.name.contains(".png")|| it.name.contains(".jpg")){

                allPartsImages.add(it.name)

            }

        }

        for(i in 0 until allPartsImages.size){

            if(!allPartsImages[i].contains(figureName)){

                var mass = allPartsImages[i].split("_")

                allPartsImages[i] = ""

                for (j in 0 until mass.size){

                    allPartsImages[i] += mass[j]
                    if(j == 0) {

                        allPartsImages[i] += "_${figureName}_"

                    }else if(j != mass.size - 1){

                        allPartsImages[i] += "_"

                    }

                }

            }

        }

        allPartsImages.sort()

    }

    fun read360Images(){

        File("$inputMainFolderName/$figureName$file360ImagesFolderName").listFiles().forEach{

            if(fileIsImage(it)){

                all360Images.add(it.name)

            }

        }

    }

    fun createCharacter(): Character{

        var character = Character()

        // Character main data
        character.name = getCharacterName(allPartsImages[0])

        character.parts = getAllBodyParts()

        // 360 data

        character.file360 = create360File(all360Images)

        return character

    }

    private fun create360File(images360: ArrayList<String>): File360{

        var result = File360()

        result.images360.addAll(images360)

        return result

    }

    private fun getAllBodyParts(): ArrayList<BodyPart>{

        var bodyParts = ArrayList<BodyPart>()

        val bodyPartsNames = getAllBodyParts(allPartsImages)

        bodyPartsNames.forEach { partName: String->

            val bodyPart = BodyPart()

            bodyPart.type = partName

            bodyPart.actions.addAll(getActionsByBodyPart(partName, allPartsImages))

            bodyParts.add(bodyPart)

        }

        bodyParts.forEach { part ->

            part.actions.forEach {

                it.conversions = formConversionsFromAction(it)

            }


        }

        return bodyParts

    }

    private fun formConversionsFromAction(action: Action): ArrayList<Conversion>{

        var result = ArrayList<Conversion>()

        var currentConversion = Conversion()
        currentConversion.conversionNumber = getConversionNumber(action.allActionImages[0])

        result.add(currentConversion)

        action.allActionImages.forEach{

            if(conversionNumberChanged(currentConversion.conversionNumber, getConversionNumber(it))){

                currentConversion = Conversion()
                currentConversion.conversionNumber = getConversionNumber(it)
                result.add(currentConversion)

            }

            currentConversion.images.add(it)

        }

        return result

    }



    private fun conversionNumberChanged(previous: String, current: String): Boolean{

        if(previous.equals(current)) return false
        if(previous.equals("") && !current.equals("")) return true
        if(!previous.equals("") && current.equals("")) return true

        if(previous.length >= 2 && current.length >= 2 ){

            if(previous.substring(0,2).equals(current.substring(0,2))) return false

        }

        return true

    }

    private fun getActionsByBodyPart(bodyPartName: String, allImages: ArrayList<String>): ArrayList<Action>{

        var listAllPartImages: ArrayList<String> = getImagesByPartName(bodyPartName, allImages)
        var result : ArrayList<Action> = ArrayList()

        var previousActionNumber = "000"
        var currentAction = Action()

        listAllPartImages.forEach {

            var currentActionNumber = getActionNumber(it)

            if(!currentActionNumber.substring(0, 2).equals(previousActionNumber.substring(0,2))){

                currentAction = Action()
                result.add(currentAction)
                currentAction.allActionImages.add(it)
                previousActionNumber = currentActionNumber

            }else{

                currentAction.allActionImages.add(it)

            }

        }

        return result

    }

    //private fun sliceActionToConversions( ArrayList<>)

    private fun getImagesByPartName(partName: String, allParts: ArrayList<String>): ArrayList<String>{

        val result : ArrayList<String> = ArrayList()

        allParts.forEach {

            if(getBodyPartName(it).equals(partName)){

                result.add(it)

            }

        }

        return result

    }

    private fun getAllBodyParts(images: ArrayList<String>): ArrayList<String>{

        val bodyParts: ArrayList<String> = ArrayList()

        images.forEach {

            if(!bodyParts.contains(getBodyPartName(it))){

                bodyParts.add(getBodyPartName(it))

            }

        }

        return bodyParts

    }

    private fun hasConversionNumber(image: String): Boolean{

        return getConversionNumber(image).isNotEmpty()

    }

    private fun getConversionNumber(image: String): String{

        val array = image.split("_")

        if(array.size == 5) return ""

        val res = array[5].replace(".png", "").replace(".jpg", "")

        return if (startsWithNumber(res)) res else ""

    }

    private fun getActionNumber(image: String): String{

        return image.split('_')[3]

    }

    private fun getActionType(image: String): String{

        return image.split('_')[4]

    }

    private fun getCharacterName(image: String): String{

        return image.split('_')[1]

    }

    private fun getBodyPartName(image: String): String{

        return image.split('_')[2]

    }

    private fun startsWithNumber(string: String): Boolean{

        return numbers.contains(string[0])

    }

    private fun fileIsImage(file: File):Boolean{

        return file.name.contains(".png")||file.name.contains(".jpg")

    }

}
