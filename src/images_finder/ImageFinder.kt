package images_finder

import java.io.File

class ImageFinder {

    val imagesFolderName = "./images"
    val sortedImagesFolderName = "./imagesSorted"

    var categories = ArrayList<Category>()

    fun findImages(){

        formCategories()

        createFolders()

        copyImagesToCategories()

    }

    fun copyImagesToCategories(){

        categories.forEach {category ->

            category.images.forEach {

                var file = File(imagesFolderName + "/" + it.substring(0, it.indexOf("=")) + ".png")

                if(file.exists()){

                    var filename = ""
                    try{

                        filename = it.substring(it.indexOf("=")+1, it.lastIndexOf("=")) + "(" + it.substring(it.lastIndexOf("=")+1 )+ " cubes)"
                    }catch (e: Exception){

                        println(it)

                    }


                    if(File(sortedImagesFolderName + "/" + category.name + "/" + filename + ".png").exists()) filename += "1"
                    if(File(sortedImagesFolderName + "/" + category.name + "/" + filename + ".png").exists()) filename += "2"
                    if(File(sortedImagesFolderName + "/" + category.name + "/" + filename + ".png").exists()) filename += "3"



                    file.copyTo(File(sortedImagesFolderName + "/" + category.name + "/" + filename + ".png"))

                }else{

                    println("file $it does not exist")

                }

            }

        }

    }

    fun createFolders(){

        categories.forEach {

            var name = sortedImagesFolderName + "/" + it.name + "/"
            if(!File(sortedImagesFolderName).exists()){

                print("output directory does not exist")

            }

            File(name).mkdir()

        }

    }


    fun formCategories(){

        var imagesArray = ImageNames.imagesNames.replace("\n","").split(",")

        var currentCategory = Category()

        imagesArray.forEach {

            if(!it.startsWith("image")){

                currentCategory = Category()
                currentCategory.name = it
                categories.add(currentCategory)

            }else{

                currentCategory.images.add(it)

            }

        }

    }


}