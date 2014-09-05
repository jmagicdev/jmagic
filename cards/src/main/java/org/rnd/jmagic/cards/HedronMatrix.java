package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hedron Matrix")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class HedronMatrix extends Card
{
	public static final class PumpByCMC extends StaticAbility
	{
		public PumpByCMC(GameState state)
		{
			super(state, "Equipped creature gets +X/+X, where X is its converted mana cost.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			SetGenerator X = ConvertedManaCostOf.instance(equippedCreature);

			this.addEffectPart(modifyPowerAndToughness(equippedCreature, X, X));
		}
	}

	public HedronMatrix(GameState state)
	{
		super(state);

		// Equipped creature gets +X/+X, where X is its converted mana cost.
		this.addAbility(new PumpByCMC(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, new CostCollection(org.rnd.jmagic.abilities.keywords.Equip.COST_TYPE, "(4)")));
	}
}
