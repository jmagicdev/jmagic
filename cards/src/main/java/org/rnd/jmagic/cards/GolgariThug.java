package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Golgari Thug")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class GolgariThug extends Card
{
	public static final class GolgariThugAbility0 extends EventTriggeredAbility
	{
		public GolgariThugAbility0(GameState state)
		{
			super(state, "When Golgari Thug dies, put target creature card from your graveyard on top of your library.");
			this.addPattern(whenThisDies());

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card in your graveyard"));

			this.addEffect(putOnTopOfLibrary(target, "Put target creature card from your graveyard on top of your library."));
		}
	}

	public GolgariThug(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Golgari Thug dies, put target creature card from your graveyard
		// on top of your library.
		this.addAbility(new GolgariThugAbility0(state));

		// Dredge 4 (If you would draw a card, instead you may put exactly four
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 4));
	}
}
