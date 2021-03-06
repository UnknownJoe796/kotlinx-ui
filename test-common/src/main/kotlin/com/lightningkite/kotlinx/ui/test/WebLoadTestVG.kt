package com.lightningkite.kotlinx.ui.test

import com.lightningkite.kotlinx.exception.stackTraceString
import com.lightningkite.kotlinx.httpclient.HttpBody
import com.lightningkite.kotlinx.httpclient.HttpClient
import com.lightningkite.kotlinx.httpclient.HttpMethod
import com.lightningkite.kotlinx.httpclient.HttpResponse
import com.lightningkite.kotlinx.observable.list.observableListOf
import com.lightningkite.kotlinx.observable.property.transform
import com.lightningkite.kotlinx.reflection.ExternalReflection
import com.lightningkite.kotlinx.reflection.KxType
import com.lightningkite.kotlinx.reflection.KxTypeProjection
import com.lightningkite.kotlinx.reflection.ListReflection
import com.lightningkite.kotlinx.serialization.json.callJson
import com.lightningkite.kotlinx.ui.builders.text
import com.lightningkite.kotlinx.ui.builders.vertical
import com.lightningkite.kotlinx.ui.concepts.TextSize
import com.lightningkite.kotlinx.ui.geometry.AlignPair
import com.lightningkite.kotlinx.ui.views.ViewFactory
import com.lightningkite.kotlinx.ui.views.ViewGenerator

@ExternalReflection
data class Post(
        var userId: Long = 0,
        var id: Long = 0,
        var title: String = "",
        var body: String = ""
)

class WebLoadTestVG<VIEW>() : ViewGenerator<ViewFactory<VIEW>, VIEW> {
    override val title: String = "Web Load Test"

    val data = observableListOf<Post>()

    init {
        HttpClient.callJson<List<Post>>(
                url = "https://jsonplaceholder.typicode.com/posts",
                method = HttpMethod.GET,
                body = HttpBody.EMPTY,
                typeInfo = KxType(ListReflection, false, listOf(KxTypeProjection(KxType(PostReflection, false))))
        ).invoke {
            println(it)
            if (it is HttpResponse.Success) {
                data.replace(it.result)
            } else if (it is HttpResponse.Failure) {
                println(it.exception?.stackTraceString())
            }
        }
    }

    override fun generate(dependency: ViewFactory<VIEW>): VIEW = with(dependency) {
        vertical {
            -text(text = "This is the web test.", alignPair = AlignPair.CenterCenter)
            +work(
                    list(
                            data = data,
                            onBottom = {},
                            makeView = {
                                card(vertical {
                                    -text(text = it.transform { it.title }, size = TextSize.Subheader)
                                    -text(text = it.transform { it.body }, size = TextSize.Body)
                                })
                            }
                    ),
                    data.onUpdate.transform { it.isEmpty() }
            )
        }.margin(8f)
    }
}
