package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mad Prophet")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MadProphet extends Card
{
	public static final class MadProphetAbility1 extends ActivatedAbility
	{
		public MadProphetAbility1(GameState state)
		{
			super(state, "(T), Discard a card: Draw a card.");
			this.costsTap = true;
			this.addCost(discardCards(You.instance(), 1, "Discard a card."));
			this.addEffect(drawACard());
		}
	}

	public MadProphet(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// (T), Discard a card: Draw a card.
		this.addAbility(new MadProphetAbility1(state));
	}
}
