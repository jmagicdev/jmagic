package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rattleclaw Mystic")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("1G")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class RattleclawMystic extends Card
{
	public static final class RattleclawMysticAbility2 extends EventTriggeredAbility
	{
		public RattleclawMysticAbility2(GameState state)
		{
			super(state, "When Rattleclaw Mystic is turned face up, add (G)(U)(R) to your mana pool.");
			this.addPattern(whenThisIsTurnedFaceUp());
			this.addEffect(addManaToYourManaPoolFromAbility("(G)(U)(R)"));
		}
	}

	public RattleclawMystic(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (T): Add (G), (U), or (R) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GUR)"));

		// Morph (2) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)"));

		// When Rattleclaw Mystic is turned face up, add (G)(U)(R) to your mana
		// pool.
		this.addAbility(new RattleclawMysticAbility2(state));
	}
}
