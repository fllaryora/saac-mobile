package phi.saac.comunicame.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import phi.saac.comunicame.data.DeckCardRepo
import phi.saac.comunicame.data.DeckContactModel

class HomeViewModel: ViewModel() {

    private val stream = MutableLiveData<DeckContactModel>()

    val modelStream: LiveData<DeckContactModel>
        get() = stream

    private val data = DeckCardRepo.cards

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
        stream.value = DeckContactModel(
            cardTop = topCard,
            cardBottom = bottomCard
        )
    }

}