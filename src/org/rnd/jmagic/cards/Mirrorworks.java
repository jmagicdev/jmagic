package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mirrorworks")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class Mirrorworks extends Card
{
	public static final class MirrorworksAbility0 extends EventTriggeredAbility
	{
		public MirrorworksAbility0(GameState state)
		{
			super(state, "Whenever another nontoken artifact enters the battlefield under your control, you may pay (2). If you do, put a token that's a copy of that artifact onto the battlefield.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.ARTIFACT), Union.instance(Tokens.instance(), ABILITY_SOURCE_OF_THIS)), You.instance(), false));

			EventFactory copy = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token that's a copy of that artifact onto the battlefield.");
			copy.parameters.put(EventType.Parameter.CAUSE, This.instance());
			copy.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			copy.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(TriggerZoneChange.instance(This.instance())));

			EventFactory payTwo = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (2).");
			payTwo.parameters.put(EventType.Parameter.CAUSE, This.instance());
			payTwo.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("(2)")));
			payTwo.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (2). If you do, put a token that's a copy of that artifact onto the battlefield.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(payTwo));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(copy));
			this.addEffect(factory);
		}
	}

	public Mirrorworks(GameState state)
	{
		super(state);

		// Whenever another nontoken artifact enters the battlefield under your
		// control, you may pay (2). If you do, put a token that's a copy of
		// that artifact onto the battlefield.
		this.addAbility(new MirrorworksAbility0(state));
	}
}
