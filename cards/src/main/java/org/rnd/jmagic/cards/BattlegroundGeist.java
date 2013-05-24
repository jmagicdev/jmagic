package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battleground Geist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class BattlegroundGeist extends Card
{
	public static final class BattlegroundGeistAbility1 extends StaticAbility
	{
		public BattlegroundGeistAbility1(GameState state)
		{
			super(state, "Other Spirit creatures you control get +1/+0.");

			SetGenerator otherSpirit = RelativeComplement.instance(HasSubType.instance(SubType.SPIRIT), This.instance());
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator affects = Intersect.instance(otherSpirit, CreaturePermanents.instance(), youControl);
			this.addEffectPart(modifyPowerAndToughness(affects, +1, 0));
		}
	}

	public BattlegroundGeist(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other Spirit creatures you control get +1/+0.
		this.addAbility(new BattlegroundGeistAbility1(state));
	}
}
