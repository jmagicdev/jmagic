package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Death's Shadow")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class DeathsShadow extends Card
{
	public static final class ReversePump extends StaticAbility
	{
		public ReversePump(GameState state)
		{
			super(state, "Death's Shadow gets -X/-X, where X is your life total.");

			SetGenerator amount = Subtract.instance(numberGenerator(0), LifeTotalOf.instance(You.instance()));
			this.addEffectPart(modifyPowerAndToughness(This.instance(), amount, amount));
		}
	}

	public DeathsShadow(GameState state)
	{
		super(state);

		this.setPower(13);
		this.setToughness(13);

		// Death's Shadow gets -X/-X, where X is your life total.
		this.addAbility(new ReversePump(state));
	}
}
