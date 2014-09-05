package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Black Lotus")
@Types({Type.ARTIFACT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class BlackLotus extends Card
{
	public static final class BlackLotusMana extends ActivatedAbility
	{
		public BlackLotusMana(GameState state)
		{
			super(state, "(T), Sacrifice Black Lotus: Add three mana of any one color to your mana pool.");

			this.costsTap = true;

			this.addCost(sacrificeThis("Black Lotus"));

			EventFactory effect = addManaToYourManaPoolFromAbility("(WUBRG)", "Add three mana of any one color to your mana pool");
			effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			this.addEffect(effect);
		}
	}

	public BlackLotus(GameState state)
	{
		super(state);

		this.addAbility(new BlackLotusMana(state));
	}
}
