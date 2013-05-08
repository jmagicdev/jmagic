package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Unstable Footing")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class UnstableFooting extends Card
{
	public UnstableFooting(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(3)(R)");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		ContinuousEffect.Part noPreventing = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED);

		this.addEffect(createFloatingEffect("Damage can't be prevented this turn.", noPreventing));

		Target target = this.addTarget(Players.instance(), "target player");
		target.condition = ThisSpellWasKicked.instance(kickerCost);

		EventFactory damage = spellDealDamage(5, targetedBy(target), "Unstable Footing deals 5 damage to target player.");

		EventFactory maybeDamage = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Unstable Footing was kicked, it deals 5 damage to target player.");
		maybeDamage.parameters.put(EventType.Parameter.IF, ThisSpellWasKicked.instance(kickerCost));
		maybeDamage.parameters.put(EventType.Parameter.THEN, Identity.instance(damage));
		this.addEffect(maybeDamage);
	}
}
