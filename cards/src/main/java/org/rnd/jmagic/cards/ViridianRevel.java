package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Viridian Revel")
@Types({Type.ENCHANTMENT})
@ManaCost("1GG")
@ColorIdentity({Color.GREEN})
public final class ViridianRevel extends Card
{
	public static final class ViridianRevelAbility0 extends EventTriggeredAbility
	{
		public ViridianRevelAbility0(GameState state)
		{
			super(state, "Whenever an artifact is put into an opponent's graveyard from the battlefield, you may draw a card.");

			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(OpponentsOf.instance(You.instance())), ArtifactPermanents.instance(), true));

			this.addEffect(youMay(drawACard(), "You may draw a card."));
		}
	}

	public ViridianRevel(GameState state)
	{
		super(state);

		// Whenever an artifact is put into an opponent's graveyard from the
		// battlefield, you may draw a card.
		this.addAbility(new ViridianRevelAbility0(state));
	}
}
