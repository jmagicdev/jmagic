package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Viridian Longbow")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ViridianLongbow extends Card
{
	public static final class ShootableBow extends StaticAbility
	{
		public ShootableBow(GameState state)
		{
			super(state, "Equipped creature has \"(T): This creature deals 1 damage to target creature or player.\"");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.Ping.class));
		}
	}

	public ViridianLongbow(GameState state)
	{
		super(state);

		this.addAbility(new ShootableBow(state));

		// Equip (3)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
