package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Felhide Spiritbinder")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.SHAMAN})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class FelhideSpiritbinder extends Card
{
	public static final class FelhideSpiritbinderAbility0 extends EventTriggeredAbility
	{
		public FelhideSpiritbinderAbility0(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Felhide Spiritbinder becomes untapped, you may pay (1)(R). If you do, put a token onto the battlefield that's a copy of another target creature except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step.");
			this.addPattern(inspired());

			EventFactory mayPay = youMayPay("(1)(R)");

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			SetGenerator target = targetedBy(this.addTarget(otherCreatures, "another target creature"));

			EventFactory token = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of another target creature except it's an enchantment in addition to its other types.");
			token.parameters.put(EventType.Parameter.CAUSE, This.instance());
			token.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			token.parameters.put(EventType.Parameter.OBJECT, target);
			token.parameters.put(EventType.Parameter.TYPE, Identity.instance(Type.ENCHANTMENT));

			SetGenerator thatToken = NewObjectOf.instance(EffectResult.instance(token));
			EventFactory haste = createFloatingEffect(Empty.instance(), "It gains haste.", addAbilityToObject(thatToken, org.rnd.jmagic.abilities.keywords.Haste.class));

			EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of the next end step.");
			exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachEndStep()));
			exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile(delayedTriggerContext(thatToken), "Exile it.")));

			this.addEffect(ifThen(mayPay, sequence(token, haste, exileLater), "You may pay (1)(R). If you do, put a token onto the battlefield that's a copy of another target creature except it's an enchantment in addition to its other types. It gains haste. Exile it at the beginning of the next end step."));
		}
	}

	public FelhideSpiritbinder(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Inspired \u2014 Whenever Felhide Spiritbinder becomes untapped, you
		// may pay (1)(R). If you do, put a token onto the battlefield that's a
		// copy of another target creature except it's an enchantment in
		// addition to its other types. It gains haste. Exile it at the
		// beginning of the next end step.
		this.addAbility(new FelhideSpiritbinderAbility0(state));
	}
}
