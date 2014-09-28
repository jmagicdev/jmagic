package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mistfire Weaver")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.DJINN})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class MistfireWeaver extends Card
{
	public static final class MistfireWeaverAbility2 extends EventTriggeredAbility
	{
		public MistfireWeaverAbility2(GameState state)
		{
			super(state, "When Mistfire Weaver is turned face up, target creature you control gains hexproof until end of turn.");
			this.addPattern(whenThisIsTurnedFaceUp());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Hexproof.class, "Target creature you control gains hexproof until end of turn."));
		}
	}

	public MistfireWeaver(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Morph (2)(U) (You may cast this card face down as a 2/2 creature for
		// (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(2)(U)"));

		// When Mistfire Weaver is turned face up, target creature you control
		// gains hexproof until end of turn.
		this.addAbility(new MistfireWeaverAbility2(state));
	}
}
