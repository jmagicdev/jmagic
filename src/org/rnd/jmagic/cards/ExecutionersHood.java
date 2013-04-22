package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Executioner's Hood")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({})
public final class ExecutionersHood extends Card
{
	public static final class ExecutionersHoodAbility0 extends StaticAbility
	{
		public ExecutionersHoodAbility0(GameState state)
		{
			super(state, "Equipped creature has intimidate.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Intimidate.class));
		}
	}

	public ExecutionersHood(GameState state)
	{
		super(state);

		// Equipped creature has intimidate. (This creature can't be blocked
		// except by artifact creatures and/or creatures that share a color with
		// it.)
		this.addAbility(new ExecutionersHoodAbility0(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
