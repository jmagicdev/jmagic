package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Loyal Cathar")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("WW")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
@BackFace(UnhallowedCathar.class)
public final class LoyalCathar extends Card
{
	public static final class LoyalCatharAbility1 extends EventTriggeredAbility
	{
		public LoyalCatharAbility1(GameState state)
		{
			super(state, "When Loyal Cathar dies, return it to the battlefield transformed under your control at the beginning of the next end step.");
			this.addPattern(whenThisDies());

			EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TRANSFORMED, "Return it to the battlefield transformed under your control at the beginning of the next end step.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, delayedTriggerContext(FutureSelf.instance(ABILITY_SOURCE_OF_THIS)));

			EventFactory delayed = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return it to the battlefield under its owner's control at the beginning of the next end step.");
			delayed.parameters.put(EventType.Parameter.CAUSE, This.instance());
			delayed.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			delayed.parameters.put(EventType.Parameter.EFFECT, Identity.instance(factory));
			this.addEffect(delayed);
		}
	}

	public LoyalCathar(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// When Loyal Cathar dies, return it to the battlefield transformed
		// under your control at the beginning of the next end step.
		this.addAbility(new LoyalCatharAbility1(state));
	}
}
