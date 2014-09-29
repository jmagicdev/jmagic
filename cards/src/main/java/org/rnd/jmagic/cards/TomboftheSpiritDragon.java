package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tomb of the Spirit Dragon")
@Types({Type.LAND})
@ColorIdentity({})
public final class TomboftheSpiritDragon extends Card
{
	public static final class TomboftheSpiritDragonAbility1 extends ActivatedAbility
	{
		public TomboftheSpiritDragonAbility1(GameState state)
		{
			super(state, "(2), (T): You gain 1 life for each colorless creature you control.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			SetGenerator colorlessGuys = Intersect.instance(Colorless.instance(), CREATURES_YOU_CONTROL);
			this.addEffect(gainLife(You.instance(), Count.instance(colorlessGuys), "You gain 1 life for each colorless creature you control."));
		}
	}

	public TomboftheSpiritDragon(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (2), (T): You gain 1 life for each colorless creature you control.
		this.addAbility(new TomboftheSpiritDragonAbility1(state));
	}
}
