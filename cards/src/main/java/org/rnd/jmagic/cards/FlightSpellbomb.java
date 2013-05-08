package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flight Spellbomb")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FlightSpellbomb extends Card
{
	public static final class FlightSpellbombAbility0 extends ActivatedAbility
	{
		public FlightSpellbombAbility0(GameState state)
		{
			super(state, "(T), Sacrifice Flight Spellbomb: Target creature gains flying until end of turn.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Flight Spellbomb"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
		}
	}

	public FlightSpellbomb(GameState state)
	{
		super(state);

		// (T), Sacrifice Flight Spellbomb: Target creature gains flying until
		// end of turn.
		this.addAbility(new FlightSpellbombAbility0(state));

		// When Flight Spellbomb is put into a graveyard from the battlefield,
		// you may pay (U). If you do, draw a card.
		this.addAbility(new org.rnd.jmagic.abilities.ScarsSpellbomb(state, this.getName(), "(U)"));
	}
}
