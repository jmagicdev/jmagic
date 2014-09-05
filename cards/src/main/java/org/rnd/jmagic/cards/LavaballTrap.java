package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Lavaball Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("6RR")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class LavaballTrap extends Card
{
	public LavaballTrap(GameState state)
	{
		super(state);

		// If an opponent had two or more lands enter the battlefield under his
		// or her control this turn, you may pay (3)(R)(R) rather than pay
		// Lavaball Trap's mana cost.
		state.ensureTracker(new LandsPutOntoTheBattlefieldThisTurnCounter());
		SetGenerator opponents = OpponentsOf.instance(You.instance());
		SetGenerator maxPerOpponent = MaximumPerPlayer.instance(LandsPutOntoTheBattlefieldThisTurnCounter.class, opponents);
		SetGenerator trapCondition = Intersect.instance(Between.instance(2, null), maxPerOpponent);
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), trapCondition, "If an opponent had two or more lands enter the battlefield under his or her control this turn", "(3)(R)(R)"));

		// Destroy two target lands.
		Target target = this.addTarget(LandPermanents.instance(), "two target lands");
		target.setSingleNumber(numberGenerator(2));
		this.addEffect(destroy(targetedBy(target), "Destroy two target lands."));

		// Lavaball Trap deals 4 damage to each creature.
		this.addEffect(spellDealDamage(4, CreaturePermanents.instance(), "Lavaball Trap deals 4 damage to each creature."));
	}
}
