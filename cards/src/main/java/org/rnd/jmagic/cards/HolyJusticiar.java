package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Holy Justiciar")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class HolyJusticiar extends Card
{
	public static final class HolyJusticiarAbility0 extends ActivatedAbility
	{
		public HolyJusticiarAbility0(GameState state)
		{
			super(state, "(2)(W), (T): Tap target creature. If that creature is a Zombie, exile it.");
			this.setManaCost(new ManaPool("(2)(W)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature"));

			SetGenerator targetIsZombie = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), target);
			this.addEffect(ifThen(targetIsZombie, exile(target, "Exile it"), "If that creature is a Zombie, exile it."));
		}
	}

	public HolyJusticiar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (2)(W), (T): Tap target creature. If that creature is a Zombie, exile
		// it.
		this.addAbility(new HolyJusticiarAbility0(state));
	}
}
