package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Boros Elite")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BorosElite extends Card
{
	public static final class BorosEliteAbility0 extends EventTriggeredAbility
	{
		public BorosEliteAbility0(GameState state)
		{
			super(state, "Whenever Boros Elite and at least two other creatures attack, Boros Elite gets +2/+2 until end of turn.");
			this.addPattern(battalion());
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Boros Elite gets +2/+2 until end of turn."));
		}
	}

	public BorosElite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Battalion \u2014 Whenever Boros Elite and at least two other
		// creatures attack, Boros Elite gets +2/+2 until end of turn.
		this.addAbility(new BorosEliteAbility0(state));
	}
}
