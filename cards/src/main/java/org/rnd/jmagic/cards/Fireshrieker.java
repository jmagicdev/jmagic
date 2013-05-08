package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fireshrieker")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Fireshrieker extends Card
{
	public static final class FireshriekerAbility0 extends StaticAbility
	{
		public FireshriekerAbility0(GameState state)
		{
			super(state, "Equipped creature has double strike.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.DoubleStrike.class));
		}
	}

	public Fireshrieker(GameState state)
	{
		super(state);

		// Equipped creature has double strike. (It deals both first-strike and
		// regular combat damage.)
		this.addAbility(new FireshriekerAbility0(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery. This card enters the battlefield unattached and stays on
		// the battlefield if the creature leaves.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
