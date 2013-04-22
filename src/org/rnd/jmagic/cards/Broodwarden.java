package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Broodwarden")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI, SubType.DRONE})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Broodwarden extends Card
{
	public static final class Lordish extends StaticAbility
	{
		public Lordish(GameState state)
		{
			super(state, "Eldrazi Spawn creatures you control get +2/+1.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(CreaturePermanents.instance(), Intersect.instance(HasSubType.instance(SubType.ELDRAZI), HasSubType.instance(SubType.SPAWN))), +2, +1));
		}
	}

	public Broodwarden(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Eldrazi Spawn creatures you control get +2/+1.
		this.addAbility(new Lordish(state));
	}
}
