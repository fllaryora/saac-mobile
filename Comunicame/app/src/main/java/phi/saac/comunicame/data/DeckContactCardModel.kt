package phi.saac.comunicame.data
import androidx.annotation.DrawableRes

data class DeckContactCardModel(
    val name: String,
    @DrawableRes val backgroundColor: Int,
    @DrawableRes val icon: Int
)