package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kor Line-Slinger")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.KOR})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class KorLineSlinger extends Card
{
	public static final class KorLineSlingerAbility0 extends ActivatedAbility
	{
		public KorLineSlingerAbility0(GameState state)
		{
			super(state, "(T): Tap target creature with power 3 or less.");
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(null, 3))), "target creature with power 3 or less");

			this.addEffect(tap(targetedBy(target), "Tap target creature with power 3 or less."));
		}
	}

	public KorLineSlinger(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (T): Tap target creature with power 3 or less.
		this.addAbility(new KorLineSlingerAbility0(state));
	}
}
