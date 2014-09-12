package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paragon of Eternal Wilds")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.HUMAN})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class ParagonofEternalWilds extends Card
{
	public static final class ParagonofEternalWildsAbility0 extends StaticAbility
	{
		public ParagonofEternalWildsAbility0(GameState state)
		{
			super(state, "Other green creatures you control get +1/+1.");
			SetGenerator greenGuys = Intersect.instance(HasColor.instance(Color.GREEN), CREATURES_YOU_CONTROL);
			SetGenerator otherGreenGuys = RelativeComplement.instance(greenGuys, This.instance());
			this.addEffectPart(modifyPowerAndToughness(otherGreenGuys, +1, +1));
		}
	}

	public static final class ParagonofEternalWildsAbility1 extends ActivatedAbility
	{
		public ParagonofEternalWildsAbility1(GameState state)
		{
			super(state, "(G), (T): Another target green creature you control gains trample until end of turn.");
			this.setManaCost(new ManaPool("(G)"));
			this.costsTap = true;

			SetGenerator greenGuys = Intersect.instance(HasColor.instance(Color.GREEN), CREATURES_YOU_CONTROL);
			SetGenerator otherGreenGuys = RelativeComplement.instance(greenGuys, This.instance());
			SetGenerator target = targetedBy(this.addTarget(otherGreenGuys, "another target green creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Trample.class, "Another target green creature you control gains trample until end of turn."));
		}
	}

	public ParagonofEternalWilds(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Other green creatures you control get +1/+1.
		this.addAbility(new ParagonofEternalWildsAbility0(state));

		// (G), (T): Another target green creature you control gains trample
		// until end of turn. (If it would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new ParagonofEternalWildsAbility1(state));
	}
}
