package phi.saac.comunicame.data

import phi.saac.comunicame.R
import phi.saac.comunicame.R.drawable

object DeckCardSocialExpressions {
    private const val socialExpression = R.drawable.rounded_lile_border
    val cards: List<DeckContactCardModel>
        get() = listOf(
            DeckContactCardModel(
                icon= drawable.expresion_social_buen_dia,
                name = "BUEN DÍA", backgroundColor = socialExpression
            )
    )
}