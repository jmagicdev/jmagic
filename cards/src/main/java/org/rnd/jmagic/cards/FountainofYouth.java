package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fountain of Youth")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = TheDark.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class FountainofYouth extends Card
{
	public static final class GainOne extends ActivatedAbility
	{
		public GainOne(GameState state)
		{
			super(state, "(2), (T): You gain 1 life.");

			this.setManaCost(new ManaPool("2"));

			this.costsTap = true;

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public FountainofYouth(GameState state)
	{
		super(state);

		this.addAbility(new GainOne(state));
	}
}
