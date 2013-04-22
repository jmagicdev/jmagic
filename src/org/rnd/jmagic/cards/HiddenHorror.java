package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hidden Horror")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.RARE), @Printings.Printed(ex = Expansion.WEATHERLIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class HiddenHorror extends Card
{
	// When Hidden Horror enters the battlefield, sacrifice it unless you
	// discard a card.
	public static final class SacUnlessDiscard extends EventTriggeredAbility
	{
		public SacUnlessDiscard(GameState state)
		{
			super(state, "When Hidden Horror enters the battlefield, sacrifice it unless you discard a card.");
			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory sacrifice = sacrificeThis("Hidden Horror");
			EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
			this.addEffect(unless(You.instance(), sacrifice, discard, "Sacrifice Hidden Horror unless you discard a card."));
		}
	}

	public HiddenHorror(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new SacUnlessDiscard(state));
	}
}
