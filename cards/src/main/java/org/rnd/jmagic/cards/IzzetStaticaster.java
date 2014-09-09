package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Izzet Staticaster")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class IzzetStaticaster extends Card
{
	public static final class IzzetStaticasterAbility2 extends ActivatedAbility
	{
		public IzzetStaticasterAbility2(GameState state)
		{
			super(state, "(T): Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(1, Union.instance(target, Intersect.instance(CreaturePermanents.instance(), HasName.instance(NameOf.instance(target)))), "Izzet Staticaster deals 1 damage to target creature and each other creature with the same name as that creature."));
		}
	}

	public IzzetStaticaster(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (T): Izzet Staticaster deals 1 damage to target creature and each
		// other creature with the same name as that creature.
		this.addAbility(new IzzetStaticasterAbility2(state));
	}
}
