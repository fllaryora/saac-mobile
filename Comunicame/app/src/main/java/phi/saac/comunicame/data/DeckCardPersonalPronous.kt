package phi.saac.comunicame.data

import phi.saac.comunicame.R
import phi.saac.comunicame.R.drawable

object DeckCardPersonalPronous {
    private const val personalPronouns = R.drawable.rounded_yellow_border

    val cards: List<DeckContactCardModel>
        get() = listOf(
        DeckContactCardModel(
            icon= drawable.pronombre_personal_1_1,
            name = "YO", backgroundColor = personalPronouns,
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_1_2,
            name = "YO", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_2_1,
            name = "TÚ",   backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_2_2,
            name = "TÚ",   backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_3_1,
            name = "ÉL", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_3_2,
            name = "ELLA", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_4_1,
            name = "NOSOTROS", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_4_2,
            name = "NOSOTROS",  backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_4_3,
            name = "NOSOTRAS",  backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_5_1,
            name = "VOSOTROS/USTEDES", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_5_2,
            name = "VOSOTRAS/USTEDES",  backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_6_1,
            name = "ELLOS", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_6_2,
            name = "ELLAS", backgroundColor = personalPronouns
        ),
        DeckContactCardModel(
            icon= drawable.pronombre_personal_6_3,
            name = "ELLOS", backgroundColor = personalPronouns
        )
    )
}