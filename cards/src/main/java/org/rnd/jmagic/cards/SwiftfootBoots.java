package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Swiftfoot Boots")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SwiftfootBoots extends Card
{
	public static final class SwiftfootBootsAbility0 extends StaticAbility
	{
		public SwiftfootBootsAbility0(GameState state)
		{
			super(state, "Equipped creature has hexproof and haste.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Hexproof.class, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public SwiftfootBoots(GameState state)
	{
		super(state);

		// Equipped creature has hexproof and haste. (It can't be the target of
		// spells or abilities your opponents control, and it can attack and (T)
		// as soon as it comes under your control.)
		this.addAbility(new SwiftfootBootsAbility0(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
