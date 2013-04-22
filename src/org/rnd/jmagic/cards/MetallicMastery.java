package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Metallic Mastery")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class MetallicMastery extends Card
{
	public MetallicMastery(GameState state)
	{
		super(state);

		// Gain control of target artifact until end of turn. Untap that
		// artifact. It gains haste until end of turn.
		Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");

		ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
		controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
		controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		this.addEffect(createFloatingEffect("Gain control of target artifact until end of turn.", controlPart));

		this.addEffect(untap(targetedBy(target), "Untap that artifact."));

		this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "It gains haste until end of turn."));
	}
}
