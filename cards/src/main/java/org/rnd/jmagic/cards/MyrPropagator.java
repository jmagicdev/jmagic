package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Myr Propagator")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class MyrPropagator extends Card
{
	public static final class MyrPropagatorAbility0 extends ActivatedAbility
	{
		public MyrPropagatorAbility0(GameState state)
		{
			super(state, "(3), (T): Put a token that's a copy of Myr Propagator onto the battlefield.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;

			EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token that's a copy of Myr Propagator onto the battlefield.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(factory);
		}
	}

	public MyrPropagator(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (3), (T): Put a token that's a copy of Myr Propagator onto the
		// battlefield.
		this.addAbility(new MyrPropagatorAbility0(state));
	}
}
