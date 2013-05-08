package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Solemn Simulacrum")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.GOLEM})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class SolemnSimulacrum extends Card
{
	public static final class SolemnSimulacrumAbility0 extends EventTriggeredAbility
	{
		public SolemnSimulacrumAbility0(GameState state)
		{
			super(state, "When Solemn Simulacrum enters the battlefield, you may search your library for a basic land card, put that card onto the battlefield tapped, then shuffle your library.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(searchYourLibraryForABasicLandCardAndPutItOntoTheBattlefield(true));
		}
	}

	public static final class SolemnSimulacrumAbility1 extends EventTriggeredAbility
	{
		public SolemnSimulacrumAbility1(GameState state)
		{
			super(state, "When Solemn Simulacrum dies, you may draw a card.");
			this.addPattern(whenThisDies());
			this.addEffect(youMay(drawACard()));
		}
	}

	public SolemnSimulacrum(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Solemn Simulacrum enters the battlefield, you may search your
		// library for a basic land card, put that card onto the battlefield
		// tapped, then shuffle your library.
		this.addAbility(new SolemnSimulacrumAbility0(state));

		// When Solemn Simulacrum dies, you may draw a card.
		this.addAbility(new SolemnSimulacrumAbility1(state));
	}
}
