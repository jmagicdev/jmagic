package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snake of the Golden Grove")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class SnakeoftheGoldenGrove extends Card
{
	public static final class SnakeoftheGoldenGroveAbility1 extends EventTriggeredAbility
	{
		public SnakeoftheGoldenGroveAbility1(GameState state)
		{
			super(state, "When Snake of the Golden Grove enters the battlefield, if tribute wasn't paid, you gain 4 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();
			this.addEffect(gainLife(You.instance(), 4, "You gain 4 life."));
		}
	}

	public SnakeoftheGoldenGrove(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Tribute 3 (As this creature enters the battlefield, an opponent of
		// your choice may place three +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 3));

		// When Snake of the Golden Grove enters the battlefield, if tribute
		// wasn't paid, you gain 4 life.
		this.addAbility(new SnakeoftheGoldenGroveAbility1(state));
	}
}
