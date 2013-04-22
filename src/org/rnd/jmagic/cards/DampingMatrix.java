package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Damping Matrix")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class DampingMatrix extends Card
{
	public static final class Damp extends StaticAbility
	{
		public Damp(GameState state)
		{
			super(state, "Activated abilities of artifacts and creatures can't be activated unless they're mana abilities.");

			SetGenerator objects = Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance());

			SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(objects, false));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));
			this.addEffectPart(part);
		}
	}

	public DampingMatrix(GameState state)
	{
		super(state);

		// Activated abilities of artifacts and creatures can't be activated
		// unless they're mana abilities.
		this.addAbility(new Damp(state));
	}
}
