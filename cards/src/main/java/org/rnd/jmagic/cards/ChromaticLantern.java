package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chromatic Lantern")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({})
public final class ChromaticLantern extends Card
{
	public static final class ChromaticLanternAbility0 extends StaticAbility
	{
		public ChromaticLanternAbility0(GameState state)
		{
			super(state, "Lands you control have \"(T): Add one mana of any color to your mana pool.\"");

			this.addEffectPart(addAbilityToObject(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance())), org.rnd.jmagic.abilities.TapForAnyColor.class));
		}
	}

	public ChromaticLantern(GameState state)
	{
		super(state);

		// Lands you control have
		// "(T): Add one mana of any color to your mana pool."
		this.addAbility(new ChromaticLanternAbility0(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
