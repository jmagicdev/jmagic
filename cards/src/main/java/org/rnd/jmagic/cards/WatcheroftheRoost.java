package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Watcher of the Roost")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.SOLDIER})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class WatcheroftheRoost extends Card
{
	public static final class WatcheroftheRoostAbility2 extends EventTriggeredAbility
	{
		public WatcheroftheRoostAbility2(GameState state)
		{
			super(state, "When Watcher of the Roost is turned face up, you gain 2 life.");
			this.addPattern(whenThisIsTurnedFaceUp());
			this.addEffect(gainLife(You.instance(), 2, "You gain 2 life."));
		}
	}

	public WatcheroftheRoost(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Morph\u2014Reveal a white card in your hand. (You may cast this card
		// face down as a 2/2 creature for (3). Turn it face up any time for its
		// morph cost.)
		SetGenerator blueStuff = Intersect.instance(HasColor.instance(Color.WHITE), InZone.instance(HandOf.instance(You.instance())));
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a white card in your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, blueStuff);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection morphCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Morph.COST_TYPE, reveal);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "Morph\u2014Reveal a white card in your hand.", morphCost));

		// When Watcher of the Roost is turned face up, you gain 2 life.
		this.addAbility(new WatcheroftheRoostAbility2(state));
	}
}
