package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gilded Lotus")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class GildedLotus extends Card
{
	public static final class GildedLotusAbility0 extends ActivatedAbility
	{
		public GildedLotusAbility0(GameState state)
		{
			super(state, "(T): Add three mana of any one color to your mana pool.");
			this.costsTap = true;
			EventFactory effect = addManaToYourManaPoolFromAbility("(WUBRG)", "Add three mana of any one color to your mana pool");
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(effect);
		}
	}

	public GildedLotus(GameState state)
	{
		super(state);

		// (T): Add three mana of any one color to your mana pool.
		this.addAbility(new GildedLotusAbility0(state));
	}
}
