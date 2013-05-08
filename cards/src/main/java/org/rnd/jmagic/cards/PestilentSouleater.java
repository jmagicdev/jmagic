package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Pestilent Souleater")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.INSECT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class PestilentSouleater extends Card
{
	public static final class PestilentSouleaterAbility0 extends ActivatedAbility
	{
		public PestilentSouleaterAbility0(GameState state)
		{
			super(state, "(b/p): Pestilent Souleater gains infect until end of turn.");
			this.setManaCost(new ManaPool("(b/p)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Infect.class, "Pestilent Souleater gains infect until end of turn."));
		}
	}

	public PestilentSouleater(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (b/p): Pestilent Souleater gains infect until end of turn. ((b/p) can
		// be paid with either (B) or 2 life. A creature with infect deals
		// damage to creatures in the form of -1/-1 counters and to players in
		// the form of poison counters.)
		this.addAbility(new PestilentSouleaterAbility0(state));
	}
}
