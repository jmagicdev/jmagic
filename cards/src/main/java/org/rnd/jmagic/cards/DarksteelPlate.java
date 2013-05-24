package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Darksteel Plate")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class DarksteelPlate extends Card
{
	public static final class DarksteelPlateAbility1 extends StaticAbility
	{
		public DarksteelPlateAbility1(GameState state)
		{
			super(state, "Equipped creature is indestructible.");
			this.addEffectPart(indestructible(EquippedBy.instance(This.instance())));
		}
	}

	public DarksteelPlate(GameState state)
	{
		super(state);

		// Darksteel Plate is indestructible.
		this.addAbility(new org.rnd.jmagic.abilities.Indestructible(state, this.getName()));

		// Equipped creature is indestructible.
		this.addAbility(new DarksteelPlateAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
