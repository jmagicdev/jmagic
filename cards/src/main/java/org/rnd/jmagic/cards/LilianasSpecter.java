package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Liliana's Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class LilianasSpecter extends Card
{
	public static final class LilianasSpecterAbility1 extends EventTriggeredAbility
	{
		public LilianasSpecterAbility1(GameState state)
		{
			super(state, "When Liliana's Specter enters the battlefield, each opponent discards a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(discardCards(OpponentsOf.instance(You.instance()), 1, "Each opponent discards a card."));
		}
	}

	public LilianasSpecter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Liliana's Specter enters the battlefield, each opponent discards
		// a card.
		this.addAbility(new LilianasSpecterAbility1(state));
	}
}
