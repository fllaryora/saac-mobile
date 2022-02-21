package phi.saac.comunicame.ui.scenes
import androidx.annotation.DrawableRes

data class TinderContactCardModel(
    val name: String,
    @DrawableRes val backgroundColor: Int,
    @DrawableRes val icon: Int
)