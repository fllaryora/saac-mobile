package phi.saac.comunicame.data

object DeckCardRepo {
    val cards: List<DeckContactCardModel>
        get() = DeckCardPersonalPronous.cards +
                DeckCardVerbs.cards +
                DeckCardNouns.cards +
                DeckCardAdjectives.cards +
                DeckCardSocialExpressions.cards

}