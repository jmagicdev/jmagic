package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Whispering Specter")
@Types({Type.CREATURE})
@SubTypes({SubType.SPECTER})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class WhisperingSpecter extends Card
{
	public static final class WhisperingSpecterAbility2 extends EventTriggeredAbility
	{
		public WhisperingSpecterAbility2(GameState state)
		{
			super(state, "Whenever Whispering Specter deals combat damage to a player, you may sacrifice it. If you do, that player discards a card for each poison counter he or she has.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			EventFactory youMaySacrifice = youMay(sacrificeThis("Whispering Specter"), "You may sacrifice Whispering Specter.");

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator poison = Count.instance(CountersOn.instance(thatPlayer, Counter.CounterType.POISON));
			EventFactory discardCards = discardCards(thatPlayer, poison, "That player discards a card for each poison counter he or she has.");

			EventFactory ifSacrificeThen = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may sacrifice Whispering Specter. If you do, that player discards a card for each poison counter he or she has.");
			ifSacrificeThen.parameters.put(EventType.Parameter.IF, Identity.instance(youMaySacrifice));
			ifSacrificeThen.parameters.put(EventType.Parameter.THEN, Identity.instance(discardCards));
			this.addEffect(ifSacrificeThen);
		}
	}

	public WhisperingSpecter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Whenever Whispering Specter deals combat damage to a player, you may
		// sacrifice it. If you do, that player discards a card for each poison
		// counter he or she has.
		this.addAbility(new WhisperingSpecterAbility2(state));
	}
}
