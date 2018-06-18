package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.httpclient.HttpClient
import com.lightningkite.kotlinx.httpclient.HttpMethod
import com.lightningkite.kotlinx.httpclient.HttpResponse
import com.lightningkite.kotlinx.json.callJson
import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.ui.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.list
import kotlinx.serialization.serializer

class WebLoadTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Web Load Test"

    @Serializable
    data class Post(
            var userId: Long = 0,
            var id: Long = 0,
            var title: String = "",
            var body: String = ""
    )

    val data = observableListOf<Post>()

    init {
        HttpClient.callJson(
                url = "https://jsonplaceholder.typicode.com/posts",
                method = HttpMethod.GET,
                body = Unit,
                deserializer = Post::class.serializer().list
        ).invoke {
            println("JsonHttpTest: $it")
            if (it is HttpResponse.Success) {
                data.replace(it.result)
            }
        }
    }

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        vertical(
                PlacementPair.topFill to text(text = "This is the web test.", alignPair = AlignPair.CenterCenter),
                PlacementPair.fillFill to work(
                        list(
                                data = data,
                                onBottom = {},
                                makeView = {
                                    card(vertical(
                                            PlacementPair.topCenter to text(text = it.transform { it.title }, size = TextSize.Subheader),
                                            PlacementPair.topFill to text(text = it.transform { it.body }, size = TextSize.Body)
                                    ))
                                }
                        ),
                        data.onUpdate.transform { it.isEmpty() }
                )
        ).margin(8f)
    }
}
