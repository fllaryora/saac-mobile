package phi.saac.comunicame.data

import phi.saac.comunicame.R
import phi.saac.comunicame.R.drawable

object DeckCardNouns {
    private const val noun = R.drawable.rounded_orange_border
    val cards: List<DeckContactCardModel>
        get() = listOf(
            DeckContactCardModel(
                icon= drawable.sustantivo_perro,
                name = "PERRO", backgroundColor = noun
            ),
    )
}