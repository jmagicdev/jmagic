package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Traitorous Blood")
@Types({Type.SORCERY})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TraitorousBlood extends Card
{
	public TraitorousBlood(GameState state)
	{
		super(state);

		// Gain control of target creature until end of turn. Untap it. It gains
		// trample and haste until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, target);
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("Gain control of target creature until end of turn. It gains trample and haste until end of turn.", controlPart, addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Haste.class, org.rnd.jmagic.abilities.keywords.Trample.class)));

		this.addEffect(untap(target, "Untap that creature."));
	}
}
