package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Ordruun Veteran")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.MINOTAUR})
@ManaCost("2RW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class OrdruunVeteran extends Card
{
	public static final class OrdruunVeteranAbility0 extends EventTriggeredAbility
	{
		public OrdruunVeteranAbility0(GameState state)
		{
			super(state, "Whenever Ordruun Veteran and at least two other creatures attack, Ordruun Veteran gains double strike until end of turn.");
			this.addPattern(battalion());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.DoubleStrike.class, "Ordruun Veteran gains double strike until end of turn."));
		}
	}

	public OrdruunVeteran(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Battalion \u2014 Whenever Ordruun Veteran and at least two other
		// creatures attack, Ordruun Veteran gains double strike until end of
		// turn. (It deals both first-strike and regular combat damage.)
		this.addAbility(new OrdruunVeteranAbility0(state));
	}
}
