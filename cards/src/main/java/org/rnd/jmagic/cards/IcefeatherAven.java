package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Icefeather Aven")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SHAMAN})
@ManaCost("GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class IcefeatherAven extends Card
{
	public static final class IcefeatherAvenAbility2 extends EventTriggeredAbility
	{
		public IcefeatherAvenAbility2(GameState state)
		{
			super(state, "When Icefeather Aven is turned face up, you may return another target creature to its owner's hand.");
			this.addPattern(whenThisIsTurnedFaceUp());

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(anotherCreature, "another target creature"));

			EventFactory bounce = bounce(target, "Return another target creature to its owner's hand.");
			this.addEffect(youMay(bounce, "You may return another target creature to its owner's hand."));
		}
	}

	public IcefeatherAven(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Morph (1)(G)(U) (You may cast this card face down as a 2/2 creature
		// for (3). Turn it face up any time for its morph cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "(1)(G)(U)"));

		// When Icefeather Aven is turned face up, you may return another target
		// creature to its owner's hand.
		this.addAbility(new IcefeatherAvenAbility2(state));
	}
}
