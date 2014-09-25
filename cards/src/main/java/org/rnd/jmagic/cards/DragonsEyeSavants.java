package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dragon's Eye Savants")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class DragonsEyeSavants extends Card
{
	public static final class DragonsEyeSavantsAbility1 extends EventTriggeredAbility
	{
		public DragonsEyeSavantsAbility1(GameState state)
		{
			super(state, "When Dragon's Eye Savants is turned face up, look at target opponent's hand.");
			this.addPattern(whenThisIsTurnedFaceUp());
			SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
			this.addEffect(look(You.instance(), HandOf.instance(target), "Look at target opponent's hand."));
		}
	}

	public DragonsEyeSavants(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(6);

		// Morph\u2014Reveal a blue card in your hand. (You may cast this card
		// face down as a 2/2 creature for (3). Turn it face up any time for its
		// morph cost.)
		SetGenerator blueStuff = Intersect.instance(HasColor.instance(Color.BLUE), InZone.instance(HandOf.instance(You.instance())));
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a blue card in your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, blueStuff);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection morphCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Morph.COST_TYPE, reveal);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "Morph\u2014Reveal a blue card in your hand.", morphCost));

		// When Dragon's Eye Savants is turned face up, look at target
		// opponent's hand.
		this.addAbility(new DragonsEyeSavantsAbility1(state));
	}
}
