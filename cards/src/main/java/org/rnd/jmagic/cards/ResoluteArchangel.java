package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Resolute Archangel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class ResoluteArchangel extends Card
{
	public static final class ResoluteArchangelAbility1 extends EventTriggeredAbility
	{
		public ResoluteArchangelAbility1(GameState state)
		{
			super(state, "When Resolute Archangel enters the battlefield, if your life total is less than your starting life total, it becomes equal to your starting life total.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator startingLife = StartingLifeTotalOf.instance(You.instance());
			SetGenerator lessThanStartingLife = Intersect.instance(LifeTotalOf.instance(You.instance()), Between.instance(null, startingLife));

			EventFactory setLife = new EventFactory(EventType.SET_LIFE, "Your life total becomes equal to your starting life total.");
			setLife.parameters.put(EventType.Parameter.CAUSE, This.instance());
			setLife.parameters.put(EventType.Parameter.NUMBER, startingLife);
			setLife.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(ifThen(lessThanStartingLife, setLife, "If your life total is less than your starting life total, it becomes equal to your starting life total."));
		}
	}

	public ResoluteArchangel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Resolute Archangel enters the battlefield, if your life total is
		// less than your starting life total, it becomes equal to your starting
		// life total.
		this.addAbility(new ResoluteArchangelAbility1(state));
	}
}
