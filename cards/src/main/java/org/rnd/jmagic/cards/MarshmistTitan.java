package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Marshmist Titan")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("6B")
@ColorIdentity({Color.BLACK})
public final class MarshmistTitan extends Card
{
	public static final class MarshmistTitanAbility0 extends StaticAbility
	{
		public MarshmistTitanAbility0(GameState state)
		{
			super(state, "Marshmist Titan costs (X) less to cast, where X is your devotion to black.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.COST, DevotionTo.instance(Color.BLACK));
			this.addEffectPart(part);
		}
	}

	public MarshmistTitan(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Marshmist Titan costs (X) less to cast, where X is your devotion to
		// black. (Each (B) in the mana costs of permanents you control counts
		// toward your devotion to black.)
		this.addAbility(new MarshmistTitanAbility0(state));
	}
}
