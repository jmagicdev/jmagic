package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nessian Wilds Ravager")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class NessianWildsRavager extends Card
{
	public static final class NessianWildsRavagerAbility1 extends EventTriggeredAbility
	{
		public NessianWildsRavagerAbility1(GameState state)
		{
			super(state, "When Nessian Wilds Ravager enters the battlefield, if tribute wasn't paid, you may have Nessian Wilds Ravager fight another target creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.interveningIf = org.rnd.jmagic.abilities.keywords.Tribute.WasntPaid.instance();

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherCreature, "another target creature"));
			EventFactory fight = fight(Union.instance(ABILITY_SOURCE_OF_THIS, target), "Nessian Wilds Ravager fights another target creature.");
			this.addEffect(youMay(fight));
		}
	}

	public NessianWildsRavager(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Tribute 6 (As this creature enters the battlefield, an opponent of
		// your choice may place six +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Tribute(state, 6));

		// When Nessian Wilds Ravager enters the battlefield, if tribute wasn't
		// paid, you may have Nessian Wilds Ravager fight another target
		// creature. (Each deals damage equal to its power to the other.)
		this.addAbility(new NessianWildsRavagerAbility1(state));
	}
}
