package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Temur Charger")
@Types({Type.CREATURE})
@SubTypes({SubType.HORSE})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class TemurCharger extends Card
{
	public static final class TemurChargerAbility1 extends EventTriggeredAbility
	{
		public TemurChargerAbility1(GameState state)
		{
			super(state, "When Temur Charger is turned face up, target creature gains trample until end of turn.");
			this.addPattern(whenThisIsTurnedFaceUp());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Trample.class, "Target creature gains trample until end of turn."));
		}
	}

	public TemurCharger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Morph\u2014Reveal a green card in your hand. (You may cast this card
		// face down as a 2/2 creature for (3). Turn it face up any time for its
		// morph cost.)
		SetGenerator blueStuff = Intersect.instance(HasColor.instance(Color.GREEN), InZone.instance(HandOf.instance(You.instance())));
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a green card in your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, blueStuff);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection morphCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Morph.COST_TYPE, reveal);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "Morph\u2014Reveal a green card in your hand.", morphCost));

		// When Temur Charger is turned face up, target creature gains trample
		// until end of turn.
		this.addAbility(new TemurChargerAbility1(state));
	}
}
