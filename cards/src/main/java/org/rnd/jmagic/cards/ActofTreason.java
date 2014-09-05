package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Act of Treason")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ActofTreason extends Card
{
	public ActofTreason(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("Gain control of target creature until end of turn.", controlPart));

		this.addEffect(untap(targetedBy(target), "Untap that creature."));

		this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
	}
}
