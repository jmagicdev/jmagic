package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pharagax Giant")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4R")
@ColorIdentity({Color.RED})
public final class PharagaxGiant extends Card
{
	public static final class PharagaxGiantAbility1 extends EventTriggeredAbility
	{
		public PharagaxGiantAbility1(GameState state)
		{
			super(state, "When Pharagax Giant enters the battlefield, if tribute wasn't paid, Pharagax Giant deals 5 damage to each opponent.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();
			this.addEffect(permanentDealDamage(5, OpponentsOf.instance(You.instance()), "Pharagax Giant deals 5 damage to each opponent."));
		}
	}

	public PharagaxGiant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Tribute 2 (As this creature enters the battlefield, an opponent of
		// your choice may place two +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 2));

		// When Pharagax Giant enters the battlefield, if tribute wasn't paid,
		// Pharagax Giant deals 5 damage to each opponent.
		this.addAbility(new PharagaxGiantAbility1(state));
	}
}
