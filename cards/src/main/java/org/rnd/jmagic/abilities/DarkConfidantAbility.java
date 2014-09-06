package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DarkConfidantAbility extends EventTriggeredAbility
{
	public DarkConfidantAbility(GameState state)
	{
		super(state, "At the beginning of your upkeep, reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost.");
		this.addPattern(atTheBeginningOfYourUpkeep());

		SetGenerator library = LibraryOf.instance(You.instance());
		SetGenerator topCard = TopCards.instance(numberGenerator(1), library);

		EventFactory reveal = reveal(topCard, "Reveal the top card of your library");
		this.addEffect(reveal);
		this.addEffect(putIntoHand(topCard, OwnerOf.instance(topCard), "and put that card into your hand."));
		this.addEffect(loseLife(You.instance(), ConvertedManaCostOf.instance(EffectResult.instance(reveal)), "You lose life equal to its converted mana cost."));
	}
}