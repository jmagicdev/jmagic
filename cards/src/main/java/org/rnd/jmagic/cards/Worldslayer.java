package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Worldslayer")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class Worldslayer extends Card
{
	public static final class WorldslayerAbility0 extends EventTriggeredAbility
	{
		public WorldslayerAbility0(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, destroy all permanents other than Worldslayer.");
			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));
			this.addEffect(destroy(RelativeComplement.instance(Permanents.instance(), ABILITY_SOURCE_OF_THIS), "Destroy all permanents other than Worldslayer."));
		}
	}

	public Worldslayer(GameState state)
	{
		super(state);

		// Whenever equipped creature deals combat damage to a player, destroy
		// all permanents other than Worldslayer.
		this.addAbility(new WorldslayerAbility0(state));

		// Equip (5) ((5): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(5)"));
	}
}
