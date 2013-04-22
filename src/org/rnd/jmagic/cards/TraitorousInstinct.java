package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Traitorous Instinct")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class TraitorousInstinct extends Card
{
	public TraitorousInstinct(GameState state)
	{
		super(state);

		// Gain control of target creature until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("Gain control of target creature until end of turn.", controlPart));

		// Untap that creature.
		this.addEffect(untap(targetedBy(target), "Untap that creature."));

		// Until end of turn, it gets +2/+0 and gains haste.
		this.addEffect(ptChangeAndAbilityUntilEndOfTurn(targetedBy(target), +2, +0, "Until end of turn, it gets +2/+0 and gains haste.", org.rnd.jmagic.abilities.keywords.Haste.class));

	}
}
