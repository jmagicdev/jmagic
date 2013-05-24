package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Loam Lion")
@Types({Type.CREATURE})
@SubTypes({SubType.CAT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class LoamLion extends Card
{
	public static final class ConditionalPump extends StaticAbility
	{
		public ConditionalPump(GameState state)
		{
			super(state, "Loam Lion gets +1/+2 as long as you control a Forest.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 2));

			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator forests = HasSubType.instance(SubType.FOREST);
			SetGenerator youControlAForest = Intersect.instance(youControl, forests);
			this.canApply = Both.instance(youControlAForest, this.canApply);
		}
	}

	public LoamLion(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Loam Lion gets +1/+2 as long as you control a Forest.
		this.addAbility(new ConditionalPump(state));
	}
}
