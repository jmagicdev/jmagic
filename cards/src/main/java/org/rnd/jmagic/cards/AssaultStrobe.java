package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Assault Strobe")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AssaultStrobe extends Card
{
	public AssaultStrobe(GameState state)
	{
		super(state);

		// Target creature gains double strike until end of turn. (It deals both
		// first-strike and regular combat damage.)
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(createFloatingEffect("Target creature gains double strike until end of turn.", addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.DoubleStrike.class)));
	}
}
