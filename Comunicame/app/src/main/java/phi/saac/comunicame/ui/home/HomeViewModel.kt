package phi.saac.comunicame.ui.home

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import phi.saac.comunicame.R
import phi.saac.comunicame.ui.scenes.TinderContactCardModel
import phi.saac.comunicame.ui.scenes.TinderContactModel

class HomeViewModel: ViewModel() {

    private val stream = MutableLiveData<TinderContactModel>()

    val modelStream: LiveData<TinderContactModel>
        get() = stream

    private val data = listOf(
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_1_1,
            name = "YO", backgroundColor = Color.YELLOW,

        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_1_2,
            name = "YO", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_2_1,
            name = "TÚ",   backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_2_2,
            name = "TÚ",   backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_3_1,
            name = "ÉL", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_3_2,
            name = "ELLA", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_4_1,
            name = "NOSOTROS", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_4_2,
            name = "NOSOTROS",  backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_4_3,
            name = "NOSOTRAS",  backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_5_1,
            name = "VOSOTROS/USTEDES", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_5_2,
            name = "VOSOTRAS/USTEDES",  backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_6_1,
            name = "ELLOS", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_6_2,
            name = "ELLAS", backgroundColor = Color.YELLOW
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_6_3,
            name = "ELLOS", backgroundColor = Color.YELLOW
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