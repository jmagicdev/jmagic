package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arcbound Ravager")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2")
@ColorIdentity({})
public final class ArcboundRavager extends Card
{
	public static final class ArcboundRavagerAbility0 extends ActivatedAbility
	{
		public ArcboundRavagerAbility0(GameState state)
		{
			super(state, "Sacrifice an artifact: Put a +1/+1 counter on Arcbound Ravager.");
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Put a +1/+1 counter on Arcbound Ravager."));
		}
	}

	public ArcboundRavager(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Sacrifice an artifact: Put a +1/+1 counter on Arcbound Ravager.
		this.addAbility(new ArcboundRavagerAbility0(state));

		// Modular 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Modular(state, 1));
	}
}
