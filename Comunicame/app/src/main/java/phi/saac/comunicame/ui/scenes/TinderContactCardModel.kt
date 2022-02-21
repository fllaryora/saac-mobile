package phi.saac.comunicame.ui.scenes

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

data class TinderContactCardModel(
    val name: String,
    @ColorInt val backgroundColor: Int,
    @DrawableRes val icon: Int
)