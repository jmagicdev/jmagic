package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kessig Malcontents")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class KessigMalcontents extends Card
{
	public static final class KessigMalcontentsAbility0 extends EventTriggeredAbility
	{
		public KessigMalcontentsAbility0(GameState state)
		{
			super(state, "When Kessig Malcontents enters the battlefield, it deals damage to target player equal to the number of Humans you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator number = Count.instance(Intersect.instance(HasSubType.instance(SubType.HUMAN), ControlledBy.instance(You.instance())));
			this.addEffect(permanentDealDamage(number, target, "It deals damage to target player equal to the number of Humans you control."));
		}
	}

	public KessigMalcontents(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// When Kessig Malcontents enters the battlefield, it deals damage to
		// target player equal to the number of Humans you control.
		this.addAbility(new KessigMalcontentsAbility0(state));
	}
}
