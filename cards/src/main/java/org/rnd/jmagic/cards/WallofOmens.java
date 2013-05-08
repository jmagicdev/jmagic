package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Wall of Omens")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class WallofOmens extends Card
{
	public static final class CIPCantrip extends EventTriggeredAbility
	{
		public CIPCantrip(GameState state)
		{
			super(state, "When Wall of Omens enters the battlefield, draw a card.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(drawACard());
		}
	}

	public WallofOmens(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// When Wall of Omens enters the battlefield, draw a card.
		this.addAbility(new CIPCantrip(state));
	}
}
