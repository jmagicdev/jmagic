package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Darksteel Axe")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DarksteelAxe extends Card
{
	public static final class DarksteelAxeAbility1 extends StaticAbility
	{
		public DarksteelAxeAbility1(GameState state)
		{
			super(state, "Equipped creature gets +2/+0.");

			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +2, +0));
		}
	}

	public DarksteelAxe(GameState state)
	{
		super(state);

		// Darksteel Axe is indestructible. (Effects that say "destroy" don't
		// destroy it.)
		this.addAbility(new org.rnd.jmagic.abilities.Indestructible(state, this.getName()));

		// Equipped creature gets +2/+0.
		this.addAbility(new DarksteelAxeAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
