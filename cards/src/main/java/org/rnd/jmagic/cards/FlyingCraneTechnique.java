package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Flying Crane Technique")
@Types({Type.INSTANT})
@ManaCost("3URW")
@ColorIdentity({Color.RED, Color.WHITE, Color.BLUE})
public final class FlyingCraneTechnique extends Card
{
	public FlyingCraneTechnique(GameState state)
	{
		super(state);

		// Untap all creatures you control.
		this.addEffect(untap(CREATURES_YOU_CONTROL, "Untap all creatures you control."));

		// They gain flying and double strike until end of turn.
		this.addEffect(createFloatingEffect("They gain flying and double strike until end of turn.", //
				addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.DoubleStrike.class)));
	}
}
