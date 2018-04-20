package lk.kotlin.crossplatform.view

sealed class Image{
    class Bundled(val identifier:String): Image()
    class Url(val url:String): Image()
    class File(val filePath:String): Image()
}