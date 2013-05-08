package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jilt")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class Jilt extends Card
{
	public Jilt(GameState state)
	{
		super(state);

		// Kicker (1)(R) (You may pay an additional (1)(R) as you cast this
		// spell.)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(1)(R)");
		CostCollection kicker = ability.costCollections[0];
		this.addAbility(ability);

		// Return target creature to its owner's hand. If Jilt was kicked, it
		// deals 2 damage to another target creature.

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		bounce(target, "Return target creature to its owner's hand.");

		SetGenerator anotherTarget = targetedBy(this.addTarget(CreaturePermanents.instance(), "another target creature"));
		EventFactory damage = spellDealDamage(2, anotherTarget, "it deals 2 damage to another target creature");

		EventFactory ifThen = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Jilt was kicked, it deals 2 damage to another target creature.");
		ifThen.parameters.put(EventType.Parameter.IF, ThisSpellWasKicked.instance(kicker));
		ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(damage));
		this.addEffect(ifThen);
	}
}
