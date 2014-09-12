package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Altac Bloodseeker")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class AltacBloodseeker extends Card
{
	public static final class AltacBloodseekerAbility0 extends EventTriggeredAbility
	{
		public AltacBloodseekerAbility0(GameState state)
		{
			super(state, "Whenever a creature an opponent controls dies, Altac Bloodseeker gets +2/+0 and gains first strike and haste until end of turn.");
			SimpleZoneChangePattern death = new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), CreaturePermanents.instance(), OpponentsOf.instance(You.instance()), true);
			this.addPattern(death);

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Altac Bloodseeker gets +2/+0 and gains first strike and haste until end of turn.", org.rnd.jmagic.abilities.keywords.FirstStrike.class, org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public AltacBloodseeker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever a creature an opponent controls dies, Altac Bloodseeker gets
		// +2/+0 and gains first strike and haste until end of turn. (It deals
		// combat damage before creatures without first strike, and it can
		// attack and (T) as soon as it comes under your control.)
		this.addAbility(new AltacBloodseekerAbility0(state));
	}
}
