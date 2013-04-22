package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dawn Charm")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class DawnCharm extends Card
{
	public DawnCharm(GameState state)
	{
		super(state);

		// Choose one

		// Prevent all combat damage that would be dealt this turn
		{
			this.addEffect(1, createFloatingReplacement(new org.rnd.jmagic.abilities.PreventCombatDamage(state.game), "Prevent all combat damage that would be dealt this turn"));
		}

		// Regenerate target creature
		{
			Target target = this.addTarget(2, CreaturePermanents.instance(), "target creature");
			this.addEffect(2, regenerate(targetedBy(target), "regenerate target creature"));
		}

		// Counter target spell that targets you
		{
			Target target = this.addTarget(3, Intersect.instance(Spells.instance(), HasTarget.instance(You.instance())), "target spell that targets you");
			this.addEffect(3, counter(targetedBy(target), "counter target spell that targets you."));
		}
	}
}
