package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rapacious One")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI, SubType.DRONE})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class RapaciousOne extends Card
{
	public static final class RapaciousOneAbility1 extends EventTriggeredAbility
	{
		public RapaciousOneAbility1(GameState state)
		{
			super(state, "Whenever Rapacious One deals combat damage to a player, put that many 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\"");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(createEldraziSpawnTokens(thatMany, "Put that many 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield. They have \"Sacrifice this creature: Add (1) to your mana pool.\""));
		}
	}

	public RapaciousOne(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Rapacious One deals combat damage to a player, put that many
		// 0/1 colorless Eldrazi Spawn creature tokens onto the battlefield.
		// They have "Sacrifice this creature: Add (1) to your mana pool."
		this.addAbility(new RapaciousOneAbility1(state));
	}
}
