package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Myrsmith")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class Myrsmith extends Card
{
	public static final class MyrsmithAbility0 extends EventTriggeredAbility
	{
		public MyrsmithAbility0(GameState state)
		{
			super(state, "Whenever you cast an artifact spell, you may pay (1). If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, You.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (1)");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(1)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			token.setSubTypes(SubType.MYR);
			token.setArtifact();

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (1). If you do, put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(token));
			this.addEffect(effect);
		}
	}

	public Myrsmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever you cast an artifact spell, you may pay (1). If you do, put
		// a 1/1 colorless Myr artifact creature token onto the battlefield.
		this.addAbility(new MyrsmithAbility0(state));
	}
}
