package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scrapdiver Serpent")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("5UU")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class ScrapdiverSerpent extends Card
{
	public static final class ScrapdiverSerpentAbility0 extends StaticAbility
	{
		public ScrapdiverSerpentAbility0(GameState state)
		{
			super(state, "Scrapdiver Serpent is unblockable as long as defending player controls an artifact.");
			this.addEffectPart(unblockable(This.instance()));
			this.canApply = Intersect.instance(ControlledBy.instance(DefendingPlayer.instance(This.instance())), ArtifactPermanents.instance());
		}
	}

	public ScrapdiverSerpent(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Scrapdiver Serpent is unblockable as long as defending player
		// controls an artifact.
		this.addAbility(new ScrapdiverSerpentAbility0(state));
	}
}
