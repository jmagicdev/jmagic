package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thousand Winds")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class ThousandWinds extends Card
{
	public static final class ThousandWindsAbility2 extends EventTriggeredAbility
	{
		public ThousandWindsAbility2(GameState state)
		{
			super(state, "When Thousand Winds is turned face up, return all other tapped creatures to their owners' hands.");
			this.addPattern(whenThisIsTurnedFaceUp());

			SetGenerator tappedGuys = Intersect.instance(Tapped.instance(), HasType.instance(Type.CREATURE));
			SetGenerator otherTappedGuys = RelativeComplement.instance(tappedGuys, ABILITY_SOURCE_OF_THIS);
			this.addEffect(bounce(otherTappedGuys, "Return all other tapped creatures to their owners' hands."));
		}
	}

	public ThousandWinds(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Morph (5)(U)(U) (You may cast this card face down as a 2/2 creature
		// for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(5)(U)(U)"));

		// When Thousand Winds is turned face up, return all other tapped
		// creatures to their owners' hands.
		this.addAbility(new ThousandWindsAbility2(state));
	}
}
