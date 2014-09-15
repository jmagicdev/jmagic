package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nullify")
@Types({Type.INSTANT})
@ManaCost("UU")
@ColorIdentity({Color.BLUE})
public final class Nullify extends Card
{
	public Nullify(GameState state)
	{
		super(state);

		// Counter target creature or Aura spell.
		SetGenerator creatureOrAura = Union.instance(HasType.instance(Type.CREATURE), HasSubType.instance(SubType.AURA));
		SetGenerator legal = Intersect.instance(creatureOrAura, Spells.instance());
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature or Aura spell"));
		this.addEffect(counter(target, "Counter target creature or Aura spell."));
	}
}
