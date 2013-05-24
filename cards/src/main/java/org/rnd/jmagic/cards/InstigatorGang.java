package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Instigator Gang")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WEREWOLF})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
@BackFace(WildbloodPack.class)
public final class InstigatorGang extends Card
{
	public static final class InstigatorGangAbility0 extends StaticAbility
	{
		public InstigatorGangAbility0(GameState state)
		{
			super(state, "Attacking creatures you control get +1/+0.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(Attacking.instance(), CREATURES_YOU_CONTROL), +1, +0));
		}
	}

	public InstigatorGang(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Attacking creatures you control get +1/+0.
		this.addAbility(new InstigatorGangAbility0(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Instigator Gang.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
