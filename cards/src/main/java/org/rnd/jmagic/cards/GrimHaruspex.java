package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Haruspex")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class GrimHaruspex extends Card
{
	public static final class GrimHaruspexAbility1 extends EventTriggeredAbility
	{
		public GrimHaruspexAbility1(GameState state)
		{
			super(state, "Whenever another nontoken creature you control dies, draw a card.");

			SetGenerator tokensAndThis = Union.instance(Tokens.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator stuff = RelativeComplement.instance(CREATURES_YOU_CONTROL, tokensAndThis);
			this.addPattern(whenXDies(stuff));

			this.addEffect(drawACard());
		}
	}

	public GrimHaruspex(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Morph (B) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(B)"));

		// Whenever another nontoken creature you control dies, draw a card.
		this.addAbility(new GrimHaruspexAbility1(state));
	}
}
