package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Library of Alexandria")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ARABIAN_NIGHTS, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class LibraryofAlexandria extends Card
{
	public static final class LibraryofAlexandriaAbility1 extends ActivatedAbility
	{
		public LibraryofAlexandriaAbility1(GameState state)
		{
			super(state, "(T): Draw a card. Activate this ability only if you have exactly seven cards in hand.");
			this.costsTap = true;

			this.addEffect(drawACard());

			this.addActivateRestriction(Not.instance(Intersect.instance(Count.instance(InZone.instance(HandOf.instance(You.instance()))), numberGenerator(7))));
		}
	}

	public LibraryofAlexandria(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T): Draw a card. Activate this ability only if you have exactly
		// seven cards in hand.
		this.addAbility(new LibraryofAlexandriaAbility1(state));
	}
}
