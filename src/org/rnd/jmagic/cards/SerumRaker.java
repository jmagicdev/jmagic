package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Serum Raker")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SerumRaker extends Card
{
	public static final class SerumRakerAbility1 extends EventTriggeredAbility
	{
		public SerumRakerAbility1(GameState state)
		{
			super(state, "When Serum Raker dies, each player discards a card.");
			this.addPattern(whenThisDies());
			this.addEffect(discardCards(Players.instance(), 1, "Each player discards a card."));
		}
	}

	public SerumRaker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Serum Raker is put into a graveyard from the battlefield, each
		// player discards a card.
		this.addAbility(new SerumRakerAbility1(state));
	}
}
