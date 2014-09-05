package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Darksteel Axe")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
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

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// Equipped creature gets +2/+0.
		this.addAbility(new DarksteelAxeAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
