package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nessian Demolok")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class NessianDemolok extends Card
{
	public static final class NessianDemolokAbility1 extends EventTriggeredAbility
	{
		public NessianDemolokAbility1(GameState state)
		{
			super(state, "When Nessian Demolok enters the battlefield, if tribute wasn't paid, destroy target noncreature permanent.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			SetGenerator noncreatures = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.CREATURE));
			SetGenerator target = targetedBy(this.addTarget(noncreatures, "target noncreature permanent"));
			this.addEffect(destroy(target, "Destroy target noncreature permanent."));
		}
	}

	public NessianDemolok(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Tribute 3 (As this creature enters the battlefield, an opponent of
		// your choice may place three +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 3));

		// When Nessian Demolok enters the battlefield, if tribute wasn't paid,
		// destroy target noncreature permanent.
		this.addAbility(new NessianDemolokAbility1(state));
	}
}
