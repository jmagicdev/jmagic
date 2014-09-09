package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fangren Marauder")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5G")
@ColorIdentity({Color.GREEN})
public final class FangrenMarauder extends Card
{
	public static final class FangrenMarauderAbility0 extends EventTriggeredAbility
	{
		public FangrenMarauderAbility0(GameState state)
		{
			super(state, "Whenever an artifact is put into a graveyard from the battlefield, you may gain 5 life.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), ArtifactPermanents.instance(), true));
			this.addEffect(youMay(gainLife(You.instance(), 5, "Gain 5 life."), "You may gain 5 life."));
		}
	}

	public FangrenMarauder(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Whenever an artifact is put into a graveyard from the battlefield,
		// you may gain 5 life.
		this.addAbility(new FangrenMarauderAbility0(state));
	}
}
