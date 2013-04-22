package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lightning Greaves")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class LightningGreaves extends Card
{
	public static final class LightningGreavesAbility0 extends StaticAbility
	{
		public LightningGreavesAbility0(GameState state)
		{
			super(state, "Equipped creature has haste and shroud.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Haste.class, org.rnd.jmagic.abilities.keywords.Shroud.class));
		}
	}

	public LightningGreaves(GameState state)
	{
		super(state);

		// Equipped creature has haste and shroud. (It can't be the target of
		// spells or abilities.)
		this.addAbility(new LightningGreavesAbility0(state));

		// Equip (0) ((0): Attach to target creature you control. Equip only as
		// a sorcery. This card enters the battlefield unattached and stays on
		// the battlefield if the creature leaves.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(0)"));
	}
}
