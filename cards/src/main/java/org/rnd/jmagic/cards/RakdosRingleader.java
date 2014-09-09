package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rakdos Ringleader")
@Types({Type.CREATURE})
@SubTypes({SubType.SKELETON, SubType.WARRIOR})
@ManaCost("4BR")
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosRingleader extends Card
{
	public static final class RakdosRingleaderAbility1 extends EventTriggeredAbility
	{
		public RakdosRingleaderAbility1(GameState state)
		{
			super(state, "Whenever Rakdos Ringleader deals combat damage to a player, that player discards a card at random.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator trigger = TriggerDamage.instance(This.instance());
			SetGenerator takers = TakerOfDamage.instance(DamageDealtBy.instance(ABILITY_SOURCE_OF_THIS, trigger));

			EventFactory factory = new EventFactory(EventType.DISCARD_RANDOM, "That player discards a card at random");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, takers);
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			this.addEffect(factory);
		}
	}

	public RakdosRingleader(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Whenever Rakdos Ringleader deals combat damage to a player, that
		// player discards a card at random.
		this.addAbility(new RakdosRingleaderAbility1(state));

		// (B): Regenerate Rakdos Ringleader.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));
	}
}
