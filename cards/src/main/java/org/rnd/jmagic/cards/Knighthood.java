package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Knighthood")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Knighthood extends Card
{
	public static final class FirstStrikeForAll extends StaticAbility
	{
		public FirstStrikeForAll(GameState state)
		{
			super(state, "Creatures you control have first strike.");

			SetGenerator creaturesYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));

			this.addEffectPart(addAbilityToObject(creaturesYouControl, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public Knighthood(GameState state)
	{
		super(state);

		this.addAbility(new FirstStrikeForAll(state));
	}
}
