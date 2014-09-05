package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Giant Adephage")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class GiantAdephage extends Card
{
	public static final class GiantAdephageAbility1 extends EventTriggeredAbility
	{
		public GiantAdephageAbility1(GameState state)
		{
			super(state, "Whenever Giant Adephage deals combat damage to a player, put a token onto the battlefield that's a copy of Giant Adephage.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of Giant Adephage.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addEffect(factory);
		}
	}

	public GiantAdephage(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Giant Adephage deals combat damage to a player, put a token
		// onto the battlefield that's a copy of Giant Adephage.
		this.addAbility(new GiantAdephageAbility1(state));
	}
}
