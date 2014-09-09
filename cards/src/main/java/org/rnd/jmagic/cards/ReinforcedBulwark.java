package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reinforced Bulwark")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.WALL})
@ManaCost("3")
@ColorIdentity({})
public final class ReinforcedBulwark extends Card
{
	public static final class ReinforcedBulwarkAbility1 extends ActivatedAbility
	{
		public ReinforcedBulwarkAbility1(GameState state)
		{
			super(state, "(T): Prevent the next 1 damage that would be dealt to you this turn.");
			this.costsTap = true;

			EventFactory prevent = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 1 damage that would be dealt to you this turn.");
			prevent.parameters.put(EventType.Parameter.CAUSE, This.instance());
			prevent.parameters.put(EventType.Parameter.PREVENT, Identity.instance(1, You.instance()));
			this.addEffect(prevent);
		}
	}

	public ReinforcedBulwark(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Prevent the next 1 damage that would be dealt to you this turn.
		this.addAbility(new ReinforcedBulwarkAbility1(state));
	}
}
