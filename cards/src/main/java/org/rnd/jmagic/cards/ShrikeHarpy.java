package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shrike Harpy")
@Types({Type.CREATURE})
@SubTypes({SubType.HARPY})
@ManaCost("3BB")
@ColorIdentity({Color.BLACK})
public final class ShrikeHarpy extends Card
{
	public static final class ShrikeHarpyAbility2 extends EventTriggeredAbility
	{
		public ShrikeHarpyAbility2(GameState state)
		{
			super(state, "When Shrike Harpy enters the battlefield, if tribute wasn't paid, target opponent sacrifices a creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

			this.addEffect(sacrifice(target, 1, CreaturePermanents.instance(), "Target opponent sacrifices a creature."));
		}
	}

	public ShrikeHarpy(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Tribute 2 (As this creature enters the battlefield, an opponent of
		// your choice may place two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 2));

		// When Shrike Harpy enters the battlefield, if tribute wasn't paid,
		// target opponent sacrifices a creature.
		this.addAbility(new ShrikeHarpyAbility2(state));
	}
}
