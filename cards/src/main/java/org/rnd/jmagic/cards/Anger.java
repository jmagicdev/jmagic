package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Anger")
@Types({Type.CREATURE})
@SubTypes({SubType.INCARNATION})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Judgment.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Anger extends Card
{
	public static final class AngerAbility1 extends StaticAbility
	{
		public AngerAbility1(GameState state)
		{
			super(state, "As long as Anger is in your graveyard and you control a Mountain, creatures you control have haste.");

			SetGenerator youControlMountain = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.MOUNTAIN));
			this.canApply = Both.instance(THIS_IS_IN_A_GRAVEYARD, youControlMountain);

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public Anger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// As long as Anger is in your graveyard and you control a Mountain,
		// creatures you control have haste.
		this.addAbility(new AngerAbility1(state));
	}
}
