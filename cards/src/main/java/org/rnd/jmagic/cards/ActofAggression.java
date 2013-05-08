package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Act of Aggression")
@Types({Type.INSTANT})
@ManaCost("3(R/P)(R/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ActofAggression extends Card
{
	public ActofAggression(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls");

		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("Gain control of target creature until end of turn. It gains haste until end of turn.", controlPart, addAbilityToObject(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class)));

		this.addEffect(untap(targetedBy(target), "Untap that creature."));
	}
}
