package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bone to Ash")
@Types({Type.INSTANT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class BonetoAsh extends Card
{
	public BonetoAsh(GameState state)
	{
		super(state);

		// Counter target creature spell.
		SetGenerator creatureSpells = Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(creatureSpells, "target creature spell"));
		this.addEffect(counter(target, "Counter target creature spell."));

		// Draw a card.
		this.addEffect(drawACard());
	}
}
