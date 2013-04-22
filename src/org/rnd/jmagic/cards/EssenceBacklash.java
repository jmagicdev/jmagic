package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Essence Backlash")
@Types({Type.INSTANT})
@ManaCost("2UR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class EssenceBacklash extends Card
{
	public EssenceBacklash(GameState state)
	{
		super(state);

		// Counter target creature spell. Essence Backlash deals damage equal to
		// that spell's power to its controller.
		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(creatureSpells, "target creature spell"));
		this.addEffect(counter(target, "Counter target creature spell."));

		this.addEffect(spellDealDamage(PowerOf.instance(target), ControllerOf.instance(target), "Essence Backlash deals damage equal to that spell's power to its controller."));
	}
}
