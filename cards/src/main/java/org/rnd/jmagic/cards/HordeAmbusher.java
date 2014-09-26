package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horde Ambusher")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class HordeAmbusher extends Card
{
	public static final class HordeAmbusherAbility0 extends EventTriggeredAbility
	{
		public HordeAmbusherAbility0(GameState state)
		{
			super(state, "Whenever Horde Ambusher blocks, it deals 1 damage to you.");
			this.addPattern(whenThisBlocks());
			this.addEffect(permanentDealDamage(1, You.instance(), "Horde Ambusher deals 1 damage to you."));
		}
	}

	public static final class HordeAmbusherAbility2 extends EventTriggeredAbility
	{
		public HordeAmbusherAbility2(GameState state)
		{
			super(state, "When Horde Ambusher is turned face up, target creature can't block this turn.");
			this.addPattern(whenThisIsTurnedFaceUp());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), targetedBy(target))));

			this.addEffect(createFloatingEffect("Target creature can't block this turn.", part));
		}
	}

	public HordeAmbusher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Horde Ambusher blocks, it deals 1 damage to you.
		this.addAbility(new HordeAmbusherAbility0(state));

		// Morph\u2014Reveal a red card in your hand. (You may cast this card
		// face down as a 2/2 creature for (3). Turn it face up any time for its
		// morph cost.)
		SetGenerator redStuff = Intersect.instance(HasColor.instance(Color.RED), InZone.instance(HandOf.instance(You.instance())));
		EventFactory reveal = new EventFactory(EventType.REVEAL_CHOICE, "Reveal a red card in your hand");
		reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
		reveal.parameters.put(EventType.Parameter.OBJECT, redStuff);
		reveal.parameters.put(EventType.Parameter.PLAYER, You.instance());

		CostCollection morphCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Morph.COST_TYPE, reveal);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Morph(state, "Morph\u2014Reveal a red card in your hand.", morphCost));

		// When Horde Ambusher is turned face up, target creature can't block
		// this turn.
		this.addAbility(new HordeAmbusherAbility2(state));
	}
}
