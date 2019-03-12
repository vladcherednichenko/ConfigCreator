
import main_entities.ClayCharacter
import main_entities.Component

class DisappearanceCreator (disapList: ArrayList<String>, jokeImages: ArrayList<String>){

    val digits = "0123456789"

    val allDisapList = disapList
    val allJokeImages = jokeImages

    lateinit var character: ClayCharacter

    fun createDisaps(character: ClayCharacter){

        this.character = character

        splitDisapsByParts()

        splitImagesInPartsByComponents()

    }

    fun splitDisapsByParts(){

        allDisapList.forEach {imageName->

            character.parts.forEach {part ->

                if(part.partNumber.equals(getBodyPart(imageName))){

                    part.disapList.add(imageName)

                }

            }

        }

    }

    fun splitImagesInPartsByComponents(){

        character.parts.forEach {part->

            var previousImage = ""
            var currentComponent = Component()

            part.disapList.forEach {currentImage->

                if(!imageInSameComponent(previousImage, currentImage)){

                    currentComponent = Component()
                    part.disappearance.components.add(currentComponent)

                }

                if(part.disappearance.components.isEmpty()){

                    part.disappearance.components.add(currentComponent)

                }

                currentComponent.images.add(currentImage)
                currentComponent.type = getComponentTypePostfix(currentImage)
                if(isImageBegin(currentImage)){

                    currentComponent.isJoke = true
                    currentComponent.images.addAll(allJokeImages)

                }

                previousImage = currentImage

            }

        }

    }



    fun getBodyPart(name: String): String{

        var res = name.split("_")[1]

        if(res[0].isDigit()) {

            return res

        }else{

            println("no bodypart image found in $name")
            return ""

        }

    }

    fun imageInSameComponent(image1: String, image2: String): Boolean{

        if(image1.isEmpty() && !image2.isEmpty()) return false

        if(image1.contains("_x") && image2.contains("_x")) return true

        if(getComponentTypePostfix(image1).equals(getComponentTypePostfix(image2))) return true

        return false

    }

    // postfix
    fun getComponentTypePostfix(image: String): String{

        var res = ""

        var array = image.split("_")

        res = array[4]

        if(!res[0].isDigit()){

            res = array[3]

            if(!res[0].isDigit()) return ""

        }

        res = clearStringFromDigits(res)

        return res

    }


    fun clearStringFromDigits(string: String): String{

        var res = string

        for(i in 0..digits.length-1){

            if(res.contains(digits[i])){

                res = res.replace(digits[i] + "", "")

            }

        }

        return res

    }

    fun stringHasDigit(string: String): Boolean{

        for(i in 0..string.length){

            if(string[i].isDigit()) return true

        }

        return false

    }

    fun isImageBegin(image: String): Boolean{

        var copy = image.toLowerCase()

        return copy.contains("begin")

    }








}