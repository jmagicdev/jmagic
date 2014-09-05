package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thrummingbird")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.HORROR})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Thrummingbird extends Card
{
	public static final class ThrummingbirdAbility1 extends EventTriggeredAbility
	{
		public ThrummingbirdAbility1(GameState state)
		{
			super(state, "Whenever Thrummingbird deals combat damage to a player, proliferate.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(proliferate());
		}
	}

	public Thrummingbird(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Thrummingbird deals combat damage to a player, proliferate.
		// (You choose any number of permanents and/or players with counters on
		// them, then give each another counter of a kind already there.)
		this.addAbility(new ThrummingbirdAbility1(state));
	}
}
