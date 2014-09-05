package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Clinging Mists")
@Types({Type.INSTANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ClingingMists extends Card
{
	public ClingingMists(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt this turn.
		this.addEffect(createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(this.game), "Prevent all combat damage that would be dealt this turn."));

		// Fateful hour \u2014 If you have 5 or less life, tap all attacking
		// creatures. Those creatures don't untap during their controller's next
		// untap step.
		EventFactory tapHard = new EventFactory(EventType.TAP_HARD, "Tap all attacking creatures. Those creatures don't untap during their controller's next untap step.");
		tapHard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		tapHard.parameters.put(EventType.Parameter.OBJECT, Attacking.instance());
		this.addEffect(ifThen(FatefulHour.instance(), tapHard, "If you have 5 or less life, tap all attacking creatures. Those creatures don't untap during their controller's next untap step."));
	}
}
