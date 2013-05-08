package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zektar Shrine Expedition")
@Types({Type.ENCHANTMENT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ZektarShrineExpedition extends Card
{
	public static final class MakeElemental extends ActivatedAbility
	{
		public MakeElemental(GameState state)
		{
			super(state, "Remove three quest counters from Zektar Shrine Expedition and sacrifice it: Put a 7/1 red Elemental creature token with trample and haste onto the battlefield. Exile it at the beginning of the next end step.");

			this.addCost(removeCountersFromThis(3, Counter.CounterType.QUEST, "Zektar Shrine Expedition"));
			this.addCost(sacrificeThis("Zektar Shrine Expedition"));

			CreateTokensFactory token = new CreateTokensFactory(1, 7, 1, "Put a 7/1 red Elemental creature token with trample and haste onto the battlefield.");
			token.setColors(Color.RED);
			token.setSubTypes(SubType.ELEMENTAL);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Trample.class);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Haste.class);
			EventFactory factory = token.getEventFactory();
			this.addEffect(factory);

			SetGenerator it = EffectResult.instance(factory);
			EventFactory exile = exile(Impersonate.instance(ABILITY_SOURCE_OF_THIS, it), "Exile it.");

			EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of the next end step.");
			exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
			this.addEffect(exileLater);
		}
	}

	public ZektarShrineExpedition(GameState state)
	{
		super(state);

		// Landfall - Whenever a land enters the battlefield under your
		// control, you may put a quest counter on Zektar Shrine Expedition.
		this.addAbility(new org.rnd.jmagic.abilities.LandfallForQuestCounter(state, this.getName()));

		// Remove three quest counters from Zektar Shrine Expedition and
		// sacrifice it: Put a 7/1 red Elemental creature token with trample and
		// haste onto the battlefield. Exile it at the beginning of the next end
		// step.
		this.addAbility(new MakeElemental(state));
	}
}
