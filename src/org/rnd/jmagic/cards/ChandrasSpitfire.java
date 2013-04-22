package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chandra's Spitfire")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ChandrasSpitfire extends Card
{
	public static final class ChandrasSpitfireAbility1 extends EventTriggeredAbility
	{
		public ChandrasSpitfireAbility1(GameState state)
		{
			super(state, "Whenever an opponent is dealt noncombat damage, Chandra's Spitfire gets +3/+0 until end of turn.");
			this.addPattern(whenIsDealtNoncombatDamage(OpponentsOf.instance(You.instance())));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +0, "Chandra's Spitfire gets +3/+0 until end of turn."));
		}
	}

	public ChandrasSpitfire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever an opponent is dealt noncombat damage, Chandra's Spitfire
		// gets +3/+0 until end of turn.
		this.addAbility(new ChandrasSpitfireAbility1(state));
	}
}
