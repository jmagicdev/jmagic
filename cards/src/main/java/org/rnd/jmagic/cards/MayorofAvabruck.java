package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mayor of Avabruck")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ADVISOR, SubType.WEREWOLF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
@BackFace(HowlpackAlpha.class)
public final class MayorofAvabruck extends Card
{
	public static final class MayorofAvabruckAbility0 extends StaticAbility
	{
		public MayorofAvabruckAbility0(GameState state)
		{
			super(state, "Other Human creatures you control get +1/+1.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.HUMAN), CREATURES_YOU_CONTROL), This.instance()), +1, +1));
		}
	}

	public MayorofAvabruck(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Other Human creatures you control get +1/+1.
		this.addAbility(new MayorofAvabruckAbility0(state));

		// At the beginning of each upkeep, if no spells were cast last turn,
		// transform Mayor of Avabruck.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeFuzzy(state, this.getName()));
	}
}
