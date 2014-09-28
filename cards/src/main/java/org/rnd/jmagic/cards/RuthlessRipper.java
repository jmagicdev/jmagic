package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ruthless Ripper")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ASSASSIN})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class RuthlessRipper extends Card
{
	public static final class RuthlessRipperAbility2 extends EventTriggeredAbility
	{
		public RuthlessRipperAbility2(GameState state)
		{
			super(state, "When Ruthless Ripper is turned face up, target player loses 2 life.");
			this.addPattern(whenThisIsTurnedFaceUp());
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 2, "Target player loses 2 life."));
		}
	}

	public RuthlessRipper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Morph\u2014Reveal a black card in your hand. (You may cast this card
		// face down as a 2/2 creature for (3). Turn it face up any time for its
		// morph cost.)
		SetGenerator blueStuff = Intersect.instance(HasColor.instance(Color.BLACK), InZone.instance(HandOf.instance(You.instance())));
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a black card in your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, blueStuff);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection morphCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Morph.COST_TYPE, reveal);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "Morph\u2014Reveal a black card in your hand.", morphCost));

		// When Ruthless Ripper is turned face up, target player loses 2 life.
		this.addAbility(new RuthlessRipperAbility2(state));
	}
}
