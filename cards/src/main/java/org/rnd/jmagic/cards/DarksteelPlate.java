package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Darksteel Plate")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class DarksteelPlate extends Card
{
	public static final class DarksteelPlateAbility1 extends StaticAbility
	{
		public DarksteelPlateAbility1(GameState state)
		{
			super(state, "Equipped creature has indestructible.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public DarksteelPlate(GameState state)
	{
		super(state);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// Equipped creature has indestructible.
		this.addAbility(new DarksteelPlateAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
