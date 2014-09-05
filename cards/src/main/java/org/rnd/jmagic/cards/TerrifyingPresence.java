package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Terrifying Presence")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TerrifyingPresence extends Card
{
	public TerrifyingPresence(GameState state)
	{
		super(state);

		// Prevent all combat damage that would be dealt by creatures other than
		// target creature this turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator otherCritters = RelativeComplement.instance(CreaturePermanents.instance(), target);
		ReplacementEffect prevent = new org.rnd.jmagic.abilities.PreventCombatDamage(this.game, otherCritters, "Prevent all combat damage that would be dealt by creatures other than target creature this turn.");
		this.addEffect(createFloatingReplacement(prevent, "Prevent all combat damage that would be dealt by creatures other than target creature this turn."));
	}
}
