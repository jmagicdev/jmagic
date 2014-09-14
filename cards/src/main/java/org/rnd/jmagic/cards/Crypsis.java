package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Crypsis")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Crypsis extends Card
{
	public Crypsis(GameState state)
	{
		super(state);

		// Target creature you control gains protection from creatures your
		// opponents control until end of turn.
		SetGenerator youControlCreatures = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(youControlCreatures, "target creature you control"));

		SetPattern creatures = new TypePattern(Type.CREATURE);
		SetPattern opponents = new ControlledByPattern(OpponentsOf.instance(You.instance()));
		MultipleSetPattern pattern = new MultipleSetPattern(true);
		pattern.addPattern(creatures);
		pattern.addPattern(opponents);
		AbilityFactory protectionFactory = new org.rnd.jmagic.abilities.keywords.Protection.AbilityFactory(pattern, Identity.instance("creatures your opponents control"));
		ContinuousEffect.Part part = addAbilityToObject(target, protectionFactory);
		this.addEffect(createFloatingEffect("Target creature you control gains protection from creatures your opponents control until end of turn.", part));

		// Untap it.
		this.addEffect(untap(target, "Untap it."));
	}
}
