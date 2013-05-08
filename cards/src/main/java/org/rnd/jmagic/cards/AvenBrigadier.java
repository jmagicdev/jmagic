package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aven Brigadier")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.BIRD})
@ManaCost("3WWW")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class AvenBrigadier extends Card
{
	public static final class BirdLord extends StaticAbility
	{
		public BirdLord(GameState state)
		{
			super(state, "Other Bird creatures get +1/+1.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.BIRD)), This.instance()), +1, +1));
		}
	}

	public static final class SoldierLord extends StaticAbility
	{
		public SoldierLord(GameState state)
		{
			super(state, "Other Soldier creatures get +1/+1.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(Intersect.instance(CreaturePermanents.instance(), HasSubType.instance(SubType.SOLDIER)), This.instance()), +1, +1));
		}
	}

	public AvenBrigadier(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Other Bird creatures get +1/+1.
		this.addAbility(new BirdLord(state));

		// Other Soldier creatures get +1/+1.
		this.addAbility(new SoldierLord(state));
	}
}
