package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Terra Eternal")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class TerraEternal extends Card
{
	public static final class TerraEternalAbility0 extends StaticAbility
	{
		public TerraEternalAbility0(GameState state)
		{
			super(state, "All lands have indestructible.");
			this.addEffectPart(addAbilityToObject(LandPermanents.instance(), org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public TerraEternal(GameState state)
	{
		super(state);

		// All lands are indestructible.
		this.addAbility(new TerraEternalAbility0(state));
	}
}
