package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Samite Healer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class SamiteHealer extends Card
{
	public static final class HealThySelf extends ActivatedAbility
	{
		public HealThySelf(GameState state)
		{
			super(state, "(T): Prevent the next 1 damage that would be dealt to target creature or player this turn.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 1 damage that would be dealt to target creature or player this turn");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 1));
			this.addEffect(factory);
		}
	}

	public SamiteHealer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new HealThySelf(state));
	}
}
