package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Runed Servitor")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RunedServitor extends Card
{
	public static final class RunedServitorAbility0 extends EventTriggeredAbility
	{
		public RunedServitorAbility0(GameState state)
		{
			super(state, "When Runed Servitor dies, each player draws a card.");
			this.addPattern(whenThisDies());
			this.addEffect(drawCards(Players.instance(), 1, "Each player draws a card."));
		}
	}

	public RunedServitor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Runed Servitor is put into a graveyard from the battlefield,
		// each player draws a card.
		this.addAbility(new RunedServitorAbility0(state));
	}
}
