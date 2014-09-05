package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Death's Caress")
@Types({Type.SORCERY})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DeathsCaress extends Card
{
	public DeathsCaress(GameState state)
	{
		super(state);

		// Destroy target creature. If that creature was a Human, you gain life
		// equal to its toughness.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));

		SetGenerator itWasAHuman = EvaluatePattern.instance(new SubTypePattern(SubType.HUMAN), target);
		this.addEffect(ifThen(itWasAHuman, gainLife(You.instance(), ToughnessOf.instance(target), "You gain life equal to its toughness."), "If that creature was a Human, you gain life equal to its toughness."));
	}
}
