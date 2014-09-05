package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Thatcher Revolt")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ThatcherRevolt extends Card
{
	public ThatcherRevolt(GameState state)
	{
		super(state);

		// Put three 1/1 red Human creature tokens with haste onto the
		// battlefield.
		CreateTokensFactory f = new CreateTokensFactory(3, 1, 1, "Put three 1/1 red Human creature tokens with haste onto the battlefield.");
		f.setColors(Color.RED);
		f.setSubTypes(SubType.HUMAN);
		f.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
		EventFactory createTokens = f.getEventFactory();
		this.addEffect(createTokens);

		// Sacrifice those tokens at the beginning of the next end step.
		SetGenerator thoseTokens = delayedTriggerContext(EffectResult.instance(createTokens));

		EventFactory sacrifice = new EventFactory(EventType.SACRIFICE_PERMANENTS, "Sacrifice those tokens");
		sacrifice.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrifice.parameters.put(EventType.Parameter.PLAYER, You.instance());
		sacrifice.parameters.put(EventType.Parameter.PERMANENT, thoseTokens);

		EventFactory sacrificeLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Sacrifice those tokens at the beginning of the next end step.");
		sacrificeLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrificeLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		sacrificeLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sacrifice));
		this.addEffect(sacrificeLater);
	}
}
