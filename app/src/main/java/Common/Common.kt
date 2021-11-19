package Common

import Interface.NewsService
import Remote.RetrofitClient

object Common {

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "49e6eb7f32d44cc79862c7d16271cf4b"

    val newsService:NewsService
        get()=RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsAPI(source:String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("apiKey")
            .append(API_KEY)
            .toString()
        return apiUrl
    }

}