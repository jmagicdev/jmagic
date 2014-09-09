package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Murasa Pyromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ALLY, SubType.HUMAN})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class MurasaPyromancer extends Card
{
	public static final class NormandyLanding extends EventTriggeredAbility
	{
		public NormandyLanding(GameState state)
		{
			super(state, "Whenever Murasa Pyromancer or another Ally enters the battlefield under your control, you may have Murasa Pyromancer deal damage to target creature equal to the number of Allies you control.");

			this.addPattern(allyTrigger());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			SetGenerator number = Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ALLY)));

			EventFactory damageFactory = permanentDealDamage(number, targetedBy(target), "Murasa Pyromancer deal damage to target creature equal to the number of Allies you control");
			this.addEffect(youMay(damageFactory, "You may have Murasa Pyromancer deal damage to target creature equal to the number of Allies you control."));
		}
	}

	public MurasaPyromancer(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new NormandyLanding(state));
	}
}
