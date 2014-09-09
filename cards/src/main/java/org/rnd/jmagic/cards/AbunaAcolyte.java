package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Abuna Acolyte")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT, SubType.CLERIC})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AbunaAcolyte extends Card
{
	public static final class AbunaAcolyteAbility0 extends ActivatedAbility
	{
		public AbunaAcolyteAbility0(GameState state)
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

	public static final class AbunaAcolyteAbility1 extends ActivatedAbility
	{
		public AbunaAcolyteAbility1(GameState state)
		{
			super(state, "(T): Prevent the next 2 damage that would be dealt to target artifact creature this turn.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target artifact creature");

			EventFactory factory = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 2 damage that would be dealt to target artifact creature this turn");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PREVENT, Identity.instance(targetedBy(target), 2));
			this.addEffect(factory);
		}
	}

	public AbunaAcolyte(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Prevent the next 1 damage that would be dealt to target creature
		// or player this turn.
		this.addAbility(new AbunaAcolyteAbility0(state));

		// (T): Prevent the next 2 damage that would be dealt to target artifact
		// creature this turn.
		this.addAbility(new AbunaAcolyteAbility1(state));
	}
}
