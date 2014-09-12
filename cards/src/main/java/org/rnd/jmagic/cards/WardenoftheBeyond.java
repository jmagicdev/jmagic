package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Warden of the Beyond")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class WardenoftheBeyond extends Card
{
	public static final class WardenoftheBeyondAbility1 extends StaticAbility
	{
		public WardenoftheBeyondAbility1(GameState state)
		{
			super(state, "Warden of the Beyond gets +2/+2 as long as an opponent owns a card in exile.");

			SetGenerator enemyExiled = Intersect.instance(OwnedBy.instance(OpponentsOf.instance(You.instance())), InZone.instance(ExileZone.instance()));
			this.canApply = Both.instance(this.canApply, enemyExiled);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));
		}
	}

	public WardenoftheBeyond(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Vigilance (Attacking doesn't cause this creature to tap.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// Warden of the Beyond gets +2/+2 as long as an opponent owns a card in
		// exile.
		this.addAbility(new WardenoftheBeyondAbility1(state));
	}
}
