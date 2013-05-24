package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sire of the Storm")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SireoftheStorm extends Card
{
	public static final class SpiritcraftDraw extends EventTriggeredAbility
	{
		public SpiritcraftDraw(GameState state)
		{
			super(state, "Whenever you cast a Spirit or Arcane spell, you may draw a card.");
			this.addPattern(spiritcraft());
			this.addEffect(youMay(drawCards(You.instance(), 1, "Draw a card"), "You may draw a card."));
		}
	}

	public SireoftheStorm(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever you cast a Spirit or Arcane spell, you may draw a card.
		this.addAbility(new SpiritcraftDraw(state));
	}
}
