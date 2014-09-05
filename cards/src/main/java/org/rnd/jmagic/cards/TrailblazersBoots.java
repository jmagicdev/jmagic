package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Trailblazer's Boots")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class TrailblazersBoots extends Card
{
	public static final class TrailblazersBootsAbility0 extends StaticAbility
	{
		public TrailblazersBootsAbility0(GameState state)
		{
			super(state, "Equipped creature has nonbasic landwalk.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Landwalk.NonbasicLandwalk.class));
		}
	}

	public TrailblazersBoots(GameState state)
	{
		super(state);

		// Equipped creature has nonbasic landwalk.
		this.addAbility(new TrailblazersBootsAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
