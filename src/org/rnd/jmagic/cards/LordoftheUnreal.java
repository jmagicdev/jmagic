package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lord of the Unreal")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class LordoftheUnreal extends Card
{
	public static final class LordoftheUnrealAbility0 extends StaticAbility
	{
		public LordoftheUnrealAbility0(GameState state)
		{
			super(state, "Illusion creatures you control get +1/+1 and have hexproof.");

			SetGenerator yourIllusions = Intersect.instance(HasSubType.instance(SubType.ILLUSION), CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(yourIllusions, +1, +1));
			this.addEffectPart(addAbilityToObject(yourIllusions, org.rnd.jmagic.abilities.keywords.Hexproof.class));
		}
	}

	public LordoftheUnreal(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Illusion creatures you control get +1/+1 and have hexproof. (They
		// can't be the targets of spells or abilities your opponents control.)
		this.addAbility(new LordoftheUnrealAbility0(state));
	}
}
