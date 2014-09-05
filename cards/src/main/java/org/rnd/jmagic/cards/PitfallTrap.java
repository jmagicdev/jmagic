package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Pitfall Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class PitfallTrap extends Card
{
	public PitfallTrap(GameState state)
	{
		super(state);

		// If exactly one creature is attacking, you may pay (W) rather than pay
		// Pitfall Trap's mana cost.
		SetGenerator trapCondition = Intersect.instance(numberGenerator(1), Count.instance(Attacking.instance()));
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If exactly one creature is attacking", "(W)"));

		// Destroy target attacking creature without flying.
		Target target = this.addTarget(RelativeComplement.instance(Attacking.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target attacking creature without flying");
		this.addEffect(destroy(targetedBy(target), "Destroy target attacking creature without flying."));
	}
}
