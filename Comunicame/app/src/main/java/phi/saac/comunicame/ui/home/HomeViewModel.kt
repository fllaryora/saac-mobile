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

    private val personalPronouns = R.drawable.rounded_yellow_border
    private val noun = R.drawable.rounded_orange_border
    private val verb = R.drawable.rounded_green_border
    private val adjective = R.drawable.rounded_blue_border
    private val socialExpression = R.drawable.rounded_lile_border

    private val data = listOf(
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_1_1,
            name = "YO", backgroundColor = personalPronouns,
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_1_2,
            name = "YO", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_2_1,
            name = "TÚ",   backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_2_2,
            name = "TÚ",   backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_3_1,
            name = "ÉL", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_3_2,
            name = "ELLA", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_4_1,
            name = "NOSOTROS", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_4_2,
            name = "NOSOTROS",  backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_4_3,
            name = "NOSOTRAS",  backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_5_1,
            name = "VOSOTROS/USTEDES", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_5_2,
            name = "VOSOTRAS/USTEDES",  backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_6_1,
            name = "ELLOS", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_6_2,
            name = "ELLAS", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.pronombre_personal_6_3,
            name = "ELLOS", backgroundColor = personalPronouns
        ),
        TinderContactCardModel(
            icon= R.drawable.verbo_ser_1,
            name = "SER", backgroundColor = verb
        ),
        TinderContactCardModel(
            icon= R.drawable.verbo_estar_1,
            name = "ESTAR", backgroundColor = verb
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