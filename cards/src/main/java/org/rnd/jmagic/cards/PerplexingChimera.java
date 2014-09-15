package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Perplexing Chimera")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.CHIMERA})
@ManaCost("4U")
@ColorIdentity({Color.BLUE})
public final class PerplexingChimera extends Card
{
	public static final class PerplexingChimeraAbility0 extends EventTriggeredAbility
	{
		public PerplexingChimeraAbility0(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, you may exchange control of Perplexing Chimera and that spell. If you do, you may choose new targets for the spell.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			pattern.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(pattern);

			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);

			EventFactory exchange = new EventFactory(EventType.EXCHANGE_CONTROL, "Exchange control of Perplexing Chimera and that spell");
			exchange.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exchange.parameters.put(EventType.Parameter.OBJECT, Union.instance(ABILITY_SOURCE_OF_THIS, thatSpell));

			EventFactory newTargets = new EventFactory(EventType.CHANGE_TARGETS, "Choose new targets for the spell");
			newTargets.parameters.put(EventType.Parameter.OBJECT, thatSpell);
			newTargets.parameters.put(EventType.Parameter.PLAYER, You.instance());

			this.addEffect(ifThen(youMay(exchange), youMay(newTargets), "You may exchange control of Perplexing Chimera and that spell. If you do, you may choose new targets for the spell."));
		}
	}

	public PerplexingChimera(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Whenever an opponent casts a spell, you may exchange control of
		// Perplexing Chimera and that spell. If you do, you may choose new
		// targets for the spell. (If the spell becomes a permanent, you control
		// that permanent.)
		this.addAbility(new PerplexingChimeraAbility0(state));
	}
}
