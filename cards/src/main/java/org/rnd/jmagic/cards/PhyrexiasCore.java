package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Phyrexia's Core")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PhyrexiasCore extends Card
{
	public static final class PhyrexiasCoreAbility1 extends ActivatedAbility
	{
		public PhyrexiasCoreAbility1(GameState state)
		{
			super(state, "(1), (T), Sacrifice an artifact: You gain 1 life.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public PhyrexiasCore(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1), (T), Sacrifice an artifact: You gain 1 life.
		this.addAbility(new PhyrexiasCoreAbility1(state));
	}
}
