package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brawler's Plate")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@ColorIdentity({})
public final class BrawlersPlate extends Card
{
	public static final class BrawlersPlateAbility0 extends StaticAbility
	{
		public BrawlersPlateAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2 and has trample.");
			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));
			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public BrawlersPlate(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2 and has trample. (If it would assign
		// enough damage to its blockers to destroy them, you may have it assign
		// the rest of its damage to defending player or planeswalker.)
		this.addAbility(new BrawlersPlateAbility0(state));

		// Equip (4) ((4): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
