package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deadly Allure")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class DeadlyAllure extends Card
{
	public DeadlyAllure(GameState state)
	{
		super(state);

		// Target creature gains deathtouch until end of turn and must be
		// blocked this turn if able.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part deathtouchPart = addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Deathtouch.class);

		ContinuousEffect.Part lurePart = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_REQUIREMENT);
		lurePart.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);
		lurePart.parameters.put(ContinuousEffectType.Parameter.RANGE, Between.instance(1, null));

		this.addEffect(createFloatingEffect("Target creature gains deathtouch until end of turn and must be blocked this turn if able.", deathtouchPart, lurePart));

		// Flashback (G) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(G)"));
	}
}
