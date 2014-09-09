package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Eidolon of the Great Revel")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.SPIRIT})
@ManaCost("RR")
@ColorIdentity({Color.RED})
public final class EidolonoftheGreatRevel extends Card
{
	public static final class EidolonoftheGreatRevelAbility0 extends EventTriggeredAbility
	{
		public EidolonoftheGreatRevelAbility0(GameState state)
		{
			super(state, "Whenever a player casts a spell with converted mana cost 3 or less, Eidolon of the Great Revel deals 2 damage to that player.");

			SetGenerator cheapThings = HasConvertedManaCost.instance(Between.instance(0, 3));

			SimpleEventPattern castCheapThing = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			castCheapThing.put(EventType.Parameter.OBJECT, cheapThings);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(permanentDealDamage(2, thatPlayer, "Eidolon of the Great Revel deals 2 damage to that player."));
		}
	}

	public EidolonoftheGreatRevel(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever a player casts a spell with converted mana cost 3 or less,
		// Eidolon of the Great Revel deals 2 damage to that player.
		this.addAbility(new EidolonoftheGreatRevelAbility0(state));
	}
}
