import file_360_entities.File360
import java.io.File
import main_entities.*
import start_entities.Start

class Reader(inputFolderName: String){

    val numbers = "0123456789"

    var figureName = ""

    val inputMainFolderName = inputFolderName
    var partsFolder = ""

    val partsImagesFolderName = "/out/Parts"           // for main part in main file
    val jokeImagesFolderName = "/out/Joke"             // for joke in main file
    val constructionImagesFolder = "/out/Construction" // for disappearance
    val file360ImagesFolderName = "/out/360"           // for 360 file
    val animationImagesFolder  = "/out/Animation"      // for animation in finish
    val lipsImagesFolder = "/out/Lips"                 // for lips in finish



    var allPartsImages = ArrayList<String>()
    var allConstructionImages = ArrayList<String>()
    var allJokeImages = ArrayList<String>()
    var all360Images = ArrayList<String>()
    var allAnimationImages = ArrayList<String>()
    var allLipsImages = ArrayList<String>()



    fun read(){

        readPartsImages()
        read360Images()
        readConstructionImages()
        readJokeImages()
        readLipsImages()
        readAnimationImages()

        checkPartsImages()
        checkJokeImages()
        checkConstructionImages()


        allPartsImages.sort()
        allConstructionImages.sort()
        all360Images.sort()
        allJokeImages.sort()
        allAnimationImages.sort()
        allLipsImages.sort()

    }

    private fun readPartsImages(){

        if(!File(inputMainFolderName).exists()){println ("Parts folder not found")}

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

    }

    private fun readJokeImages(){

        if(!File("$inputMainFolderName/$figureName$jokeImagesFolderName").exists()){println ("Joke folder not found")}

        File("$inputMainFolderName/$figureName$jokeImagesFolderName").listFiles().forEach{

            if(fileIsImage(it)){

                allJokeImages.add(it.name)

            }

        }

    }

    private fun read360Images(){

        if(!File("$inputMainFolderName/$figureName$file360ImagesFolderName").exists()){println ("$inputMainFolderName/$figureName$file360ImagesFolderName folder not found")}

        File("$inputMainFolderName/$figureName$file360ImagesFolderName").listFiles().forEach{

            if(fileIsImage(it)){

                all360Images.add(it.name)

            }

        }

    }

    private fun readConstructionImages(){

        if(!File("$inputMainFolderName/$figureName$constructionImagesFolder").exists()){println ("Construction folder not found")}

        File("$inputMainFolderName/$figureName$constructionImagesFolder").listFiles().forEach {

            if(fileIsImage(it) && (it.name.startsWith("b")||(it.name.startsWith("B")))){

                allConstructionImages.add(it.name)

            }

        }

    }

    private fun readLipsImages(){

        if(!File("$inputMainFolderName/$figureName$lipsImagesFolder").exists()){println ("$inputMainFolderName/$figureName$lipsImagesFolder folder not found")}

        File("$inputMainFolderName/$figureName$lipsImagesFolder").listFiles().forEach {

            if(fileIsImage(it)){

                allLipsImages.add(it.name)

            }

        }

    }

    private fun readAnimationImages(){

        if(!File("$inputMainFolderName/$figureName$animationImagesFolder").exists()){println ("Animation folder not found")}

        File("$inputMainFolderName/$figureName$animationImagesFolder").listFiles().forEach {

            if(fileIsImage(it)){

                allAnimationImages.add(it.name)

            }

        }

    }





    private fun checkPartsImages(){

        var counter = 0

        for(i in 0 until allPartsImages.size){

            if(!allPartsImages[i].contains(figureName)){

                counter++

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

        if(counter > 0) println("$counter parts images do not have expected format")

    }

    private fun checkJokeImages(){ if(allJokeImages.isEmpty()) println("joke images not found") }

    private fun checkConstructionImages(){

        var counter = 0

        allConstructionImages.forEach {image->

            var array = image.toLowerCase().split("_")

            if(
                    array.size < 6 ||
                    !image.toLowerCase().contains(figureName.toLowerCase()) ||
                    !image.startsWith("b"))

            counter ++

        }

        if(counter > 0) println("$counter construction images do not have expected format")

    }





    fun createCharacter(): ClayCharacter{

        var character = ClayCharacter()

        // ClayCharacter main data
        character.name = getCharacterName(allPartsImages[0])

        character.parts = getAllBodyParts()


        var disapCreator = DisappearanceCreator(allConstructionImages,allJokeImages)

        disapCreator.createDisaps(character)


        // 360 data
        character.file360 = create360File(all360Images)


        // finish data
        character.finish.animationImages = allAnimationImages
        character.finish.lipsImages = allLipsImages


        // start data
        character.start = Start(figureName)

        return character

    }

    // 360

    private fun create360File(images360: ArrayList<String>): File360{

        var result = File360()

        result.images360.addAll(images360)


        return result

    }

    // Main

    private fun getAllBodyParts(): ArrayList<BodyPart>{

        var bodyParts = ArrayList<BodyPart>()

        val bodyPartsNames = getAllBodyParts(allPartsImages)

        bodyPartsNames.forEach { partName: String->

            val bodyPart = BodyPart()

            bodyPart.type = partName

            allPartsImages.forEach {

                if(getBodyPartName(it).equals(partName)){

                    bodyPart.partNumber = getBodyPartNumber(it)

                }

            }

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
        currentAction.type = getActionType(listAllPartImages[0])

        listAllPartImages.forEach {currentImage->

            var currentActionNumber = getActionNumber(currentImage)

            if(!currentActionNumber.substring(0, 2).equals(previousActionNumber.substring(0,2))){

                currentAction = Action()
                result.add(currentAction)
                currentAction.type = getActionType(currentImage)
                currentAction.allActionImages.add(currentImage)
                previousActionNumber = currentActionNumber

            }else{

                currentAction.allActionImages.add(currentImage)

            }

        }

        return result

    }

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

        val array = image.split('_')

        if(array.size < 5){

            println("did not found action type in $image")
            return ""

        }

        val res = array[4]

        if(res.isEmpty() || res.length < 2) return res

        return res.substring(0,2).toUpperCase()

    }

    private fun getCharacterName(image: String): String{

        return image.split('_')[1]

    }

    private fun getBodyPartName(image: String): String{

        return image.split('_')[2]

    }

    private fun getBodyPartNumber(image: String): String{

        return image.split('_')[0]

    }

    private fun startsWithNumber(string: String): Boolean{

        return numbers.contains(string[0])

    }

    private fun fileIsImage(file: File):Boolean{

        return file.name.contains(".png")||file.name.contains(".jpg")

    }


}
