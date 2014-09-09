package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Looter il-Kor")
@Types({Type.CREATURE})
@SubTypes({SubType.KOR, SubType.ROGUE})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class LooterilKor extends Card
{
	public static final class DrawDiscardTrigger extends EventTriggeredAbility
	{
		public DrawDiscardTrigger(GameState state)
		{
			super(state, "Whenever Looter il-Kor deals damage to an opponent, draw a card, then discard a card.");

			this.addPattern(whenDealsDamageToAnOpponent(ABILITY_SOURCE_OF_THIS));

			this.addEffect(drawCards(You.instance(), 1, "Draw a card,"));
			this.addEffect(discardCards(You.instance(), 1, "then discard a card."));
		}
	}

	public LooterilKor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shadow(state));
		this.addAbility(new DrawDiscardTrigger(state));
	}
}
