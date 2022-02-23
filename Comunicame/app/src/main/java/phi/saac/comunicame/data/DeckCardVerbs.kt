package phi.saac.comunicame.data

import phi.saac.comunicame.R
import phi.saac.comunicame.R.drawable

object DeckCardVerbs {
    private const val verb = R.drawable.rounded_green_border
    val cards: List<DeckContactCardModel>
        get() = listOf(
            DeckContactCardModel(
                icon= drawable.verbo_ser_1,
                name = "SER", backgroundColor = verb
            ),
            DeckContactCardModel(
                icon= drawable.verbo_estar_1,
                name = "ESTAR", backgroundColor = verb
            ),
    )
}