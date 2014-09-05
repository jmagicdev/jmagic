package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ajani's Presence")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = JourneyIntoNyx.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AjanisPresence extends Card
{
	public final class Strive extends org.rnd.jmagic.abilityTemplates.Strive
	{
		public Strive(GameState state)
		{
			super(state, "Ajani's Presence", "(2)(W)");
		}
	}

	public AjanisPresence(GameState state)
	{
		super(state);

		// Strive — Ajani's Presence costs (2)(W) more to cast for each target
		// beyond the first.
		this.addAbility(new Strive(state));

		// Any number of target creatures each get +1/+1 and gain indestructible
		// until end of turn.
		Target t = this.addTarget(CreaturePermanents.instance(), "any number of target creatures");
		t.setNumber(0, null);
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(t), +1, +1, "Any number of target creatures each get +1/+1 and gain indestructible until end of turn.", org.rnd.jmagic.abilities.keywords.Indestructible.class));
	}
}
