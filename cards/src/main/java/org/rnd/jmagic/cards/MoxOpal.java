package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mox Opal")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("0")
@ColorIdentity({})
public final class MoxOpal extends Card
{
	public static final class MoxOpalAbility0 extends ActivatedAbility
	{
		public MoxOpalAbility0(GameState state)
		{
			super(state, "Metalcraft \u2014 (T): Add one mana of any color to your mana pool. Activate this ability only if you control three or more artifacts.");
			this.costsTap = true;

			this.addEffect(addManaToYourManaPoolFromAbility("(WUBRG)"));

			this.addActivateRestriction(Not.instance(Metalcraft.instance()));
		}
	}

	public MoxOpal(GameState state)
	{
		super(state);

		// Metalcraft \u2014 (T): Add one mana of any color to your mana pool.
		// Activate this ability only if you control three or more artifacts.
		this.addAbility(new MoxOpalAbility0(state));
	}
}
