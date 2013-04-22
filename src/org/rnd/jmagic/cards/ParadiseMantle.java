package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Paradise Mantle")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ParadiseMantle extends Card
{
	public static final class ParadiseMantleAbility0 extends StaticAbility
	{
		public ParadiseMantleAbility0(GameState state)
		{
			super(state, "Equipped creature has \"(T): Add one mana of any color to your mana pool.\"");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.TapForAnyColor.class));
		}
	}

	public ParadiseMantle(GameState state)
	{
		super(state);

		// Equipped creature has '(T): Add one mana of any color to your mana
		// pool.'
		this.addAbility(new ParadiseMantleAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
