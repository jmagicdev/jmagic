package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Stigma Lasher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.SHAMAN})
@ManaCost("RR")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class StigmaLasher extends Card
{
	public static final class StigmaLasherAbility1 extends EventTriggeredAbility
	{
		public StigmaLasherAbility1(GameState state)
		{
			super(state, "Whenever Stigma Lasher deals damage to a player, that player can't gain life for the rest of the game.");
			this.addPattern(whenDealsDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));

			SimpleEventPattern gainPattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
			gainPattern.put(EventType.Parameter.PLAYER, thatPlayer);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(gainPattern));
			this.addEffect(createFloatingEffect(Empty.instance(), "That player can't gain life for the rest of the game", part));
		}
	}

	public StigmaLasher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Wither (This deals damage to creatures in the form of -1/-1
		// counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Wither(state));

		// Whenever Stigma Lasher deals damage to a player, that player can't
		// gain life for the rest of the game.
		this.addAbility(new StigmaLasherAbility1(state));
	}
}
