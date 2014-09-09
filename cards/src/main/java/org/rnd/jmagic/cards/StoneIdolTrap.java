package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stone Idol Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("5R")
@ColorIdentity({Color.RED})
public final class StoneIdolTrap extends Card
{
	public static final class StoneIdolTrapAbility0 extends StaticAbility
	{
		public StoneIdolTrapAbility0(GameState state)
		{
			super(state, "Stone Idol Trap costs (1) less to cast for each attacking creature.");

			ContinuousEffect.Part reduction = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			reduction.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			reduction.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("1")));
			reduction.parameters.put(ContinuousEffectType.Parameter.NUMBER, Count.instance(Attacking.instance()));
			this.addEffectPart(reduction);

			this.canApply = THIS_IS_ON_THE_STACK;
		}
	}

	public StoneIdolTrap(GameState state)
	{
		super(state);

		// Stone Idol Trap costs (1) less to cast for each attacking creature.
		this.addAbility(new StoneIdolTrapAbility0(state));

		// Put a 6/12 colorless Construct artifact creature token with trample
		// onto the battlefield. Exile it at the beginning of your next end
		// step.

		CreateTokensFactory token = new CreateTokensFactory(1, 6, 12, "Put a 6/12 colorless Construct artifact creature token with trample onto the battlefield.");
		token.setSubTypes(SubType.CONSTRUCT);
		token.setArtifact();
		token.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
		EventFactory factory = token.getEventFactory();
		this.addEffect(factory);

		// This generator is evaluated in the context of the delayed trigger, so
		// its source is this card.
		SetGenerator stoneIdolTrap = ABILITY_SOURCE_OF_THIS;
		SetGenerator thatToken = effectResultFrom(factory, stoneIdolTrap);
		EventFactory exileFactory = exile(thatToken, "Exile it.");

		EventFactory delayedFactory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of your next end step.");
		delayedFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		delayedFactory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfYourEndStep()));
		delayedFactory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exileFactory));
		this.addEffect(delayedFactory);
	}
}
