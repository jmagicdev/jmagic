package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rhox Pikemaster")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.RHINO})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class RhoxPikemaster extends Card
{
	public static final class Train extends StaticAbility
	{
		public Train(GameState state)
		{
			super(state, "Other Soldier creatures you control have first strike.");

			SetGenerator soldiers = HasSubType.instance(SubType.SOLDIER);
			SetGenerator soldierCreatures = Intersect.instance(soldiers, CreaturePermanents.instance());
			SetGenerator otherSoldierCreatures = RelativeComplement.instance(soldierCreatures, This.instance());
			SetGenerator otherSoldierCreaturesYouControl = Intersect.instance(otherSoldierCreatures, ControlledBy.instance(You.instance()));

			this.addEffectPart(addAbilityToObject(otherSoldierCreaturesYouControl, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public RhoxPikemaster(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new Train(state));
	}
}
