package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Coralhelm Commander")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.MERFOLK})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CoralhelmCommander extends Card
{
	public static final class MerfolkLord extends StaticAbility
	{
		public MerfolkLord(GameState state)
		{
			super(state, "Other Merfolk creatures you control get +1/+1.");

			SetGenerator otherMerfolkCreaturesYouControl = Intersect.instance(RelativeComplement.instance(Intersect.instance(HasSubType.instance(SubType.MERFOLK), CreaturePermanents.instance()), This.instance()), ControlledBy.instance(You.instance()));

			this.addEffectPart(modifyPowerAndToughness(otherMerfolkCreaturesYouControl, 1, 1));
		}

		@Override
		public StaticAbility clone(GameState state)
		{
			return super.clone(state);
		}
	}

	public CoralhelmCommander(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)"));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 2, 3, 3, 3, "Flying", org.rnd.jmagic.abilities.keywords.Flying.class));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 4, 4, 4, "Flying; Other Merfolk creatures you control get +1/+1.", org.rnd.jmagic.abilities.keywords.Flying.class, MerfolkLord.class));
	}
}
