package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Kird Ape")
@Types({Type.CREATURE})
@SubTypes({SubType.APE})
@ManaCost("R")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = ArabianNights.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class KirdApe extends Card
{
	public static final class ConditionalPump extends StaticAbility
	{
		public ConditionalPump(GameState state)
		{
			super(state, "Kird Ape gets +1/+2 as long as you control a Forest.");

			// Kird Ape gets +1/+2
			this.addEffectPart(modifyPowerAndToughness(This.instance(), 1, 2));

			// as long as you control a Forest.
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator forests = HasSubType.instance(SubType.FOREST);
			SetGenerator youControlAForest = Intersect.instance(youControl, forests);
			this.canApply = Both.instance(youControlAForest, this.canApply);
		}
	}

	public KirdApe(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new ConditionalPump(state));
	}
}
