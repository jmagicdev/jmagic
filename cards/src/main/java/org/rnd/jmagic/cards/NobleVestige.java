package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Noble Vestige")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class NobleVestige extends Card
{
	public static final class HealThySelf extends ActivatedAbility
	{
		public HealThySelf(GameState state)
		{
			super(state, "(T): Prevent the next 1 damage that would be dealt to target player this turn.");

			this.costsTap = true;

			Target target = this.addTarget(Players.instance(), "target player");

			EventType.ParameterMap effectParameters = new EventType.ParameterMap();
			effectParameters.put(EventType.Parameter.CAUSE, This.instance());
			effectParameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 1));
			this.addEffect(new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, effectParameters, "Prevent the next 1 damage that would be dealt to target player this turn"));
		}
	}

	public NobleVestige(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (T): Prevent the next 1 damage that would be dealt to target player
		// this turn.
		this.addAbility(new HealThySelf(state));
	}
}
