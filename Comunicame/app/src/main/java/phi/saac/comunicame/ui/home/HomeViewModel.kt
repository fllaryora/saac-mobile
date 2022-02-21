package phi.saac.comunicame.ui.home

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import phi.saac.comunicame.ui.scenes.TinderContactCardModel
import phi.saac.comunicame.ui.scenes.TinderContactModel

class HomeViewModel: ViewModel() {

    private val stream = MutableLiveData<TinderContactModel>()

    val modelStream: LiveData<TinderContactModel>
        get() = stream

    private val data = listOf(
        TinderContactCardModel(
            name = "Rodrigo Dominguez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "CerveChat Dominguez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Sofia Jerez Test",   backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Maria Perez",   backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Rodrigo Dominguez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Rodrigo Dominguez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Perez Gonzalez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Tomas Dominguez",  backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Rodrigo Dominguez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Lopez Jose Jose",  backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Felipe Felipe Lopez", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "Nicolas Lucas Test", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            name = "John", backgroundColor = Color.YELLOW
        )
    )
    private var currentIndex = 0

    private val topCard
        get() = data[currentIndex % data.size]
    private val bottomCard
        get() = data[(currentIndex + 1) % data.size]

    init {
        updateCards()
    }

    fun swipe() {
        currentIndex += 1
        updateCards()
    }

    private fun updateCards() {
        stream.value = TinderContactModel(
            cardTop = topCard,
            cardBottom = bottomCard
        )
    }

}