package Interface

import Model.News
import Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

//    We can delete https://newsapi.org/because this is BASE_URL we have declare on Common
    @get:GET(" https://newsapi.org/v2/top-headlines/sources?apiKey=API_KEY")
    val sources:Call<WebSite>

    @GET
    fun getNewsFromSource(@Url url:String):Call<News>
}