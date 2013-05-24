package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Gust-Skimmer")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.INSECT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class GustSkimmer extends Card
{
	public static final class GustSkimmerAbility0 extends ActivatedAbility
	{
		public GustSkimmerAbility0(GameState state)
		{
			super(state, "(U): Gust-Skimmer gains flying until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Gust-Skimmer gains flying until end of turn."));
		}
	}

	public GustSkimmer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (U): Gust-Skimmer gains flying until end of turn.
		this.addAbility(new GustSkimmerAbility0(state));
	}
}
