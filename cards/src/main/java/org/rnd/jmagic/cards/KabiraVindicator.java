package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kabira Vindicator")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class KabiraVindicator extends Card
{
	public static abstract class KabiraPump extends StaticAbility
	{
		public KabiraPump(GameState state, int num)
		{
			super(state, "Other creatures you control get +" + num + "/+" + num + ".");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance()), num, num));
		}
	}

	public static final class One extends KabiraPump
	{
		public One(GameState state)
		{
			super(state, 1);
		}
	}

	public static final class Two extends KabiraPump
	{
		public Two(GameState state)
		{
			super(state, 2);
		}
	}

	public KabiraVindicator(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Level up (2)(W)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(2)(W)"));

		// LEVEL 2-4
		// 3/6
		// Other creatures you control get +1/+1.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 2, 4, 3, 6, "Other creatures you control get +1/+1.", One.class));

		// LEVEL 5+
		// 4/8
		// Other creatures you control get +2/+2.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 5, 4, 8, "Other creatures you control get +2/+2.", Two.class));
	}
}
