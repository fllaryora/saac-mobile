package phi.saac.comunicame.data

import phi.saac.comunicame.R
import phi.saac.comunicame.R.drawable

object DeckCardAdjectives {
    private const val adjective = R.drawable.rounded_blue_border
    val cards: List<DeckContactCardModel>
        get() = listOf(
            DeckContactCardModel(
                icon= drawable.cualidad_feliz,
                name = "FELÍZ", backgroundColor = adjective
            ),
    )
}