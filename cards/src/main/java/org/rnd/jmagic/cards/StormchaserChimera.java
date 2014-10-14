package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stormchaser Chimera")
@Types({Type.CREATURE})
@SubTypes({SubType.CHIMERA})
@ManaCost("2UR")
@ColorIdentity({Color.RED, Color.BLUE})
public final class StormchaserChimera extends Card
{
	public static final class StormchaserChimeraAbility1 extends ActivatedAbility
	{
		public StormchaserChimeraAbility1(GameState state)
		{
			super(state, "(2)(U)(R): Scry 1, then reveal the top card of your library. Stormchaser Chimera gets +X/+0 until end of turn, where X is that card's converted mana cost.");
			this.setManaCost(new ManaPool("(2)(U)(R)"));

			this.addEffect(scry(1, "Scry 1,"));

			SetGenerator top = TopCards.instance(1, LibraryOf.instance(You.instance()));
			this.addEffect(reveal(top, "then reveal the top card of your library."));

			SetGenerator X = ConvertedManaCostOf.instance(top);
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, X, numberGenerator(0), "Stormchaser Chimera gets +X/+0 until end of turn, where X is that card's converted mana cost."));
		}
	}

	public StormchaserChimera(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (2)(U)(R): Scry 1, then reveal the top card of your library.
		// Stormchaser Chimera gets +X/+0 until end of turn, where X is that
		// card's converted mana cost. (To scry 1, look at the top card of your
		// library, then you may put that card on the bottom of your library.)
		this.addAbility(new StormchaserChimeraAbility1(state));
	}
}
