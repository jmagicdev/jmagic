package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Enchantress's Presence")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class EnchantresssPresence extends Card
{
	public static final class EnchantresssPresenceAbility0 extends EventTriggeredAbility
	{
		public EnchantresssPresenceAbility0(GameState state)
		{
			super(state, "Whenever you cast an enchantment spell, draw a card.");

			SimpleEventPattern whenYouCastASpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			whenYouCastASpell.put(EventType.Parameter.PLAYER, You.instance());
			whenYouCastASpell.put(EventType.Parameter.OBJECT, HasType.instance(Type.ENCHANTMENT));
			this.addPattern(whenYouCastASpell);

			this.addEffect(drawACard());
		}
	}

	public EnchantresssPresence(GameState state)
	{
		super(state);

		// Whenever you cast an enchantment spell, draw a card.
		this.addAbility(new EnchantresssPresenceAbility0(state));
	}
}
