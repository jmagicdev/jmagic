package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Wrath of God")
@Types({Type.SORCERY})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class WrathofGod extends Card
{
	public WrathofGod(GameState state)
	{
		super(state);

		this.addEffects(bury(this, org.rnd.jmagic.engine.generators.CreaturePermanents.instance(), "Destroy all creatures. They can't be regenerated."));
	}
}
